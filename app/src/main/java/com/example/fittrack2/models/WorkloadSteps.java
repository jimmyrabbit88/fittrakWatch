package com.example.fittrack2.models;

public class WorkloadSteps {
    private int steps;
    private float stepsPercent;
    private int recommendedCurrentTarget;
    private float rctPercent;

    public WorkloadSteps(int steps, float stepsPercent, int recommendedCurrentTarget, float rctPercent) {
        this.steps = steps;
        this.stepsPercent = stepsPercent;
        this.recommendedCurrentTarget = recommendedCurrentTarget;
        this.rctPercent = rctPercent;
    }

    public int getSteps() {
        return steps;
    }

    public float getStepsPercent() {
        return stepsPercent;
    }

    public int getRecommendedCurrentTarget() {
        return recommendedCurrentTarget;
    }

    public float getRctPercent() {
        return rctPercent;
    }
}
