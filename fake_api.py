from flask import Flask, request, jsonify

app = Flask(__name__)

exercise = [
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
        {"workoutName": "Yoga Flow 1", "exercises": [1, 2, 3]},
        {"workoutName": "Yoga Flow 2", "exercises": [4, 5, 6]},
        {"workoutName": "Yoga Flow 3", "exercises": [1, 4, 5]},
    ],
    "pilates": [
        {"workoutName": "Pilates Beginner", "exercises": [7, 8, 9]},
        {"workoutName": "Pilates Intermediate", "exercises": [10, 11, 12]},
        {"workoutName": "Pilates Advanced", "exercises": [7, 10, 12]},
    ],
    "HIIT": [
        {"workoutName": "HIIT Blast", "exercises": [13, 14, 15]},
        {"workoutName": "HIIT Burn", "exercises": [16, 17, 18]},
        {"workoutName": "HIIT Challenge", "exercises": [13, 16, 18]},
    ],
    "bodyweight": [
        {"workoutName": "Bodyweight Basics", "exercises": [19, 20, 21]},
        {"workoutName": "Bodyweight Strength", "exercises": [22, 23, 24]},
        {"workoutName": "Bodyweight Endurance", "exercises": [19, 22, 24]},
    ],
    "cycling": [
        {"workoutName": "Cycling Session 1", "exercises": [25]},
        {"workoutName": "Cycling Session 2", "exercises": [25]},
        {"workoutName": "Cycling Session 3", "exercises": [25]},
    ],
}


@app.route('/exercise', methods=['GET'])
def get_exercise():
    return jsonify(exercise)

@app.route('/exercises/category/<category_name>', methods=['GET'])
def get_exercises_by_category(category_name):
    print(f"Received categoryName: {category_name}")
    filtered_exercises = [ex for ex in exercise if ex["categoryName"].lower() == category_name.lower()]
    return jsonify(filtered_exercises)

@app.route('/categories', methods=['GET'])
def get_categories():
    categories = set(ex["categoryName"] for ex in exercise)
    return jsonify(list(categories))

@app.route('/exercises', methods=['GET'])
def get_all_exercises():
    return jsonify(exercise)

if __name__ == '__main__':
    app.run(debug=True)
