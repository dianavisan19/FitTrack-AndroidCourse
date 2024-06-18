from flask import Flask, jsonify
from urllib.parse import quote

app = Flask(__name__)

exerciseModel = [
    {"id": 1, "categoryName": "yoga",  "name": "TreePose", "reps": 10, "sets": 3},
    {"id": 2, "categoryName": "yoga",  "name": "Warrior1Pose", "reps": 12, "sets": 3},
    {"id": 3, "categoryName": "yoga",  "name": "Warrior2Pose", "reps": 12, "sets": 3},
    {"id": 4, "categoryName": "yoga",  "name": "DownwardDog", "reps": 8, "sets": 4},
    {"id": 5, "categoryName": "yoga",  "name": "BridgePose", "reps": 15, "sets": 3},
    {"id": 6, "categoryName": "yoga",  "name": "ChildPose", "reps": 10, "sets": 3},
    {"id": 7, "categoryName": "pilates",  "name": "RollUp", "reps": 12, "sets": 3},
    {"id": 8, "categoryName": "pilates",  "name": "TheHundred", "reps": 15, "sets": 3},
    {"id": 9, "categoryName": "pilates",  "name": "Teaser", "reps": 10, "sets": 3},
    {"id": 10, "categoryName": "pilates",  "name": "Swan", "reps": 8, "sets": 4},
    {"id": 11, "categoryName": "pilates",  "name": "LegCircle", "reps": 12, "sets": 3},
    {"id": 12, "categoryName": "pilates",  "name": "CrissCross", "reps": 10, "sets": 3},
    {"id": 13, "categoryName": "HIIT",  "name": "Thrusters", "reps": 15, "sets": 3},
    {"id": 14, "categoryName": "HIIT",  "name": "Pushups", "reps": 20, "sets": 3},
    {"id": 15, "categoryName": "HIIT",  "name": "MountainClimbers", "reps": 30, "sets": 3},
    {"id": 16, "categoryName": "HIIT",  "name": "Burpees", "reps": 12, "sets": 4},
    {"id": 17, "categoryName": "HIIT",  "name": "JumpSquats", "reps": 15, "sets": 3},
    {"id": 18, "categoryName": "HIIT",  "name": "HighKnees", "reps": 30, "sets": 3},
    {"id": 19, "categoryName": "bodyweight",  "name": "Plank", "reps": 60, "sets": 3},
    {"id": 20, "categoryName": "bodyweight",  "name": "ReverseLunges", "reps": 12, "sets": 3},
    {"id": 21, "categoryName": "bodyweight",  "name": "JumpingJacks", "reps": 30, "sets": 3},
    {"id": 22, "categoryName": "bodyweight",  "name": "Squats", "reps": 15, "sets": 3},
    {"id": 23, "categoryName": "bodyweight",  "name": "PullUps", "reps": 10, "sets": 3},
    {"id": 24, "categoryName": "bodyweight",  "name": "Dips", "reps": 12, "sets": 3},
    {"id": 25, "categoryName": "cycling",  "name": "Cycling", "reps": 0, "sets": 0},
]

workouts = {
    "yoga": [
        {"workoutName": "Yoga_Flow_1", "exerciseModels": [1, 2, 3]},
        {"workoutName": "Yoga_Flow_2", "exerciseModels": [4, 5, 6]},
        {"workoutName": "Yoga_Flow_3", "exerciseModels": [1, 4, 5]},
    ],
    "pilates": [
        {"workoutName": "Pilates_Beginner", "exerciseModels": [7, 8, 9]},
        {"workoutName": "Pilates_Intermediate", "exerciseModels": [10, 11, 12]},
        {"workoutName": "Pilates_Advanced", "exerciseModels": [7, 10, 12]},
    ],
    "hiit": [
        {"workoutName": "HIIT_Blast", "exerciseModels": [13, 14, 15]},
        {"workoutName": "HIIT_Burn", "exerciseModels": [16, 17, 18]},
        {"workoutName": "HIIT_Challenge", "exerciseModels": [13, 16, 18]},
    ],
    "bodyweight": [
        {"workoutName": "Bodyweight_Basics", "exerciseModels": [19, 20, 21]},
        {"workoutName": "Bodyweight_Strength", "exerciseModels": [22, 23, 24]},
        {"workoutName": "Bodyweight_Endurance", "exerciseModels": [19, 22, 24]},
    ],
    "cycling": [
        {"workoutName": "Cycling_Session_1", "exerciseModels": [25]},
        {"workoutName": "Cycling_Session_2", "exerciseModels": [25]},
        {"workoutName": "Cycling_Session_3", "exerciseModels": [25]},
    ],
}

@app.route('/exercises/category/<category_name>', methods=['GET'])
def get_workouts_by_category(category_name):
    filtered_workouts = workouts.get(category_name.lower(), [])

    for workout in filtered_workouts:
        workout["exerciseModels"] = [ex_id for ex_id in workout["exerciseModels"]]

    return jsonify(filtered_workouts)


@app.route('/exercises/<category_name>/<workout_name>', methods=['GET'])
def get_exercises_by_category_and_workout(category_name, workout_name):
    filtered_workouts = workouts.get(category_name.lower(), [])

    matching_workout = next((workout for workout in filtered_workouts if workout["workoutName"] == workout_name), None)
    if matching_workout:
        exercises_ids = matching_workout["exerciseModels"]

        exercises = []
        for exercise_id in exercises_ids:
            exercise = next((ex for ex in exerciseModel if ex["id"] == exercise_id), None)
            if exercise:
                exercises.append({
                    "id": exercise["id"],
                    "categoryName": exercise["categoryName"],
                    "name": exercise["name"],
                    "reps": exercise["reps"],
                    "sets": exercise["sets"]
                })

        return jsonify(exercises=exercises)
    else:
        return jsonify(message=f"No workout found with name '{workout_name}' in category '{category_name}'"), 404


if __name__ == '__main__':
    app.run(debug=True)
