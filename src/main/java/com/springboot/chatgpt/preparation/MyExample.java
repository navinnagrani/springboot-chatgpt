package com.springboot.chatgpt.preparation;


/*You're given a stream of JSON events representing model inference logs:
        Example event: {"ts":"2025-12-01T12:00:05Z","model":"reranker-v3","latencyMs":42,"status":"OK"}
        Some events may have missing fields or malformed values.
        Write a small program that:
        - Reads events from STDIN (newline-delimited JSON).
        - Filters out invalid events (missing ts, latencyMs not a positive integer, status not in {OK,ERROR}).
        - Computes rolling aggregates per model in 1-minute tumbling windows based on ts:
        countOK, countERROR, p95LatencyMs (approximate acceptable).
        - Emits one JSON line per model+window end, sorted by window end ascending:
        {"windowEnd":"2025-12-01T12:01:00Z","model":"reranker-v3","countOK":123,"countERROR":4,"p95LatencyMs":75}*/

/*Input
        {"ts":"2025-12-01T12:00:05Z","model":"reranker-v3","latencyMs":42,"status":"OK"}
        {"ts":"2025-12-01T12:00:06Z","model":"reranker-v3","latencyMs":42,"status":"OK"}
        {"ts":"2025-12-01T12:00:07Z","model":"reranker-v3","latencyMs":42,"status":"OK"}

        {"ts":"2025-12-01T12:00:05Z","model":"reranker-v3","latency":42,"status":"OK"}
        {"ts":"2025-12-01T12:00:06Z","model":"reranker-v3","latency":42,"status":"OK"}
        {"ts":"2025-12-01T12:00:07Z","model":"reranker-v3","latency":42,"status":"OK"}*/

//Token :

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;

public class MyExample {
    static class Aggregation {
        int okCount = 0;
        int errorCount = 0;
        List<Integer> latencies = new ArrayList<>();

        void add(int latency, String status) {
            if ("OK".equals(status)) okCount++;
            else errorCount++;
            latencies.add(latency);
        }

        int getP95() {
            if (latencies.isEmpty()) return 0;
            Collections.sort(latencies);
            int idx = (int) Math.ceil(0.95 * latencies.size()) - 1;
            return latencies.get(Math.max(idx, 0));
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ObjectMapper mapper = new ObjectMapper();

        // model -> windowEnd -> aggregation
        Map<String, Map<Long, Aggregation>> store = new HashMap<>();

        String line;

        while ((line = br.readLine()) != null) {
            try {
                JsonNode node = mapper.readTree(line);

                // Validate fields
                if (!node.has("ts") || !node.has("model") ||
                        !node.has("latency") || !node.has("status")) continue;

                long ts = Instant.parse(node.get("ts").asText()).getEpochSecond();
                String model = node.get("model").asText();
                int latency = node.get("latency").asInt();
                String status = node.get("status").asText();

                if (latency <= 0) continue;
                if (!status.equals("OK") && !status.equals("ERROR")) continue;

                // Compute window
                long windowStart = (ts / 60) * 60;
                long windowEnd = windowStart + 60;

                store
                        .computeIfAbsent(model, k -> new HashMap<>())
                        .computeIfAbsent(windowEnd, k -> new Aggregation())
                        .add(latency, status);

            } catch (Exception e) {
                // malformed JSON → ignore
            }
        }

        // Flatten results
        List<Result> results = new ArrayList<>();

        for (var modelEntry : store.entrySet()) {
            String model = modelEntry.getKey();

            for (var windowEntry : modelEntry.getValue().entrySet()) {
                long windowEnd = windowEntry.getKey();
                Aggregation agg = windowEntry.getValue();

                results.add(new Result(
                        windowEnd,
                        model,
                        agg.okCount,
                        agg.errorCount,
                        agg.getP95()
                ));
            }
        }

        // Sort by window_end
        results.sort(Comparator.comparingLong(r -> r.windowEnd));

        // Output
        for (Result r : results) {
            System.out.println(r.toJson());
        }
    }

    static class Result {
        long windowEnd;
        String model;
        int countOk;
        int countError;
        int p95;

        Result(long w, String m, int ok, int err, int p95) {
            this.windowEnd = w;
            this.model = m;
            this.countOk = ok;
            this.countError = err;
            this.p95 = p95;
        }

        String toJson() {
            return String.format(
                    "{\"window_end\":%d,\"model\":\"%s\",\"count_ok\":%d,\"count_error\":%d,\"p95_latency_ms\":%d}",
                    windowEnd, model, countOk, countError, p95
            );
        }
    }
}
