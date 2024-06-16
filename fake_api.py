from flask import Flask, jsonify

app = Flask(__name__)

exerciseModel = [
    {"id": 1, "categoryName": "yoga",  "name": "TreePose"},
    {"id": 2, "categoryName": "yoga",  "name": "Warrior1Pose"},
    {"id": 3, "categoryName": "yoga",  "name": "Warrior2Pose"},
    {"id": 4, "categoryName": "yoga",  "name": "DownwardDog"},
    {"id": 5, "categoryName": "yoga",  "name": "BridgePose"},
    {"id": 6, "categoryName": "yoga",  "name": "ChildPose"},
    {"id": 7, "categoryName": "pilates",  "name": "Roll-Up"},
    {"id": 8, "categoryName": "pilates",  "name": "TheHundred"},
    {"id": 9, "categoryName": "pilates",  "name": "Teaser"},
    {"id": 10, "categoryName": "pilates",  "name": "Swan"},
    {"id": 11, "categoryName": "pilates",  "name": "LegCircle"},
    {"id": 12, "categoryName": "pilates",  "name": "CrissCross"},
    {"id": 13, "categoryName": "HIIT",  "name": "Thrusters"},
    {"id": 14, "categoryName": "HIIT",  "name": "Pushups"},
    {"id": 15, "categoryName": "HIIT",  "name": "MountainClimbers"},
    {"id": 16, "categoryName": "HIIT",  "name": "Burpees"},
    {"id": 17, "categoryName": "HIIT",  "name": "JumpSquats"},
    {"id": 18, "categoryName": "HIIT",  "name": "HighKnees"},
    {"id": 19, "categoryName": "bodyweight",  "name": "Plank"},
    {"id": 20, "categoryName": "bodyweight",  "name": "ReverseLunges"},
    {"id": 21, "categoryName": "bodyweight",  "name": "JumpingJacks"},
    {"id": 22, "categoryName": "bodyweight",  "name": "Squats"},
    {"id": 23, "categoryName": "bodyweight",  "name": "PullUps"},
    {"id": 24, "categoryName": "bodyweight",  "name": "Dips"},
    {"id": 25, "categoryName": "cycling",  "name": "Cycling"},
]

workouts = {
    "yoga": [
        {"workoutName": "Yoga Flow 1", "exerciseModels": [1, 2, 3]},
        {"workoutName": "Yoga Flow 2", "exerciseModels": [4, 5, 6]},
        {"workoutName": "Yoga Flow 3", "exerciseModels": [1, 4, 5]},
    ],
    "pilates": [
        {"workoutName": "Pilates Beginner", "exerciseModels": [7, 8, 9]},
        {"workoutName": "Pilates Intermediate", "exerciseModels": [10, 11, 12]},
        {"workoutName": "Pilates Advanced", "exerciseModels": [7, 10, 12]},
    ],
    "HIIT": [
        {"workoutName": "HIIT Blast", "exerciseModels": [13, 14, 15]},
        {"workoutName": "HIIT Burn", "exerciseModels": [16, 17, 18]},
        {"workoutName": "HIIT Challenge", "exerciseModels": [13, 16, 18]},
    ],
    "bodyweight": [
        {"workoutName": "Bodyweight Basics", "exerciseModels": [19, 20, 21]},
        {"workoutName": "Bodyweight Strength", "exerciseModels": [22, 23, 24]},
        {"workoutName": "Bodyweight Endurance", "exerciseModels": [19, 22, 24]},
    ],
    "cycling": [
        {"workoutName": "Cycling Session 1", "exerciseModels": [25]},
        {"workoutName": "Cycling Session 2", "exerciseModels": [25]},
        {"workoutName": "Cycling Session 3", "exerciseModels": [25]},
    ],
}

@app.route('/exercises/category/<category_name>', methods=['GET'])
def get_workouts_by_category(category_name):
    print(f"Received categoryName: {category_name}")
    filtered_workouts = workouts.get(category_name.lower(), [])

    # Extract only exercise IDs
    for workout in filtered_workouts:
        workout["exerciseModels"] = [ex_id for ex_id in workout["exerciseModels"]]

    return jsonify(filtered_workouts)
@app.route('/exercises', methods=['POST'])
def get_exercises_by_ids():
    exercise_ids = request.json.get("exerciseIds", [])
    exercises = [exercise for exercise in exerciseModel if exercise["id"] in exercise_ids]
    return jsonify(exercises)

if __name__ == '__main__':
    app.run(debug=True)
