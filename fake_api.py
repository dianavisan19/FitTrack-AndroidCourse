from flask import Flask, request, jsonify

app = Flask(__name__)

# Sample data
exercise = [
    {"id": 1, "categoryName": "yoga",  "name": "TreePose"},
    {"id": 2, "categoryName": "yoga",  "name": "Warrior1Pose"},
    {"id": 3, "categoryName": "yoga",  "name": "Warrior2Pose"},
    {"id": 4, "categoryName": "pilates",  "name": "Roll-Up"},
    {"id": 5, "categoryName": "pilates",  "name": "TheHundred"},
    {"id": 6, "categoryName": "pilates",  "name": "Teaser"},
    {"id": 7, "categoryName": "HIIT",  "name": "Thrusters"},
    {"id": 8, "categoryName": "HIIT",  "name": "Pushups"},
    {"id": 9, "categoryName": "HIIT",  "name": "MountainClimbers"},
    {"id": 10, "categoryName": "bodyweight",  "name": "Plank"},
    {"id": 11, "categoryName": "bodyweight",  "name": "ReverseLunges"},
    {"id": 12, "categoryName": "bodyweight",  "name": "JumpingJacks"},
    {"id": 13, "categoryName": "cycling",  "name": "Cycling"},
    
]

# Route to get all exercise
@app.route('/exercise', methods=['GET'])
def get_exercise():
    return jsonify(exercise)

# Route to get exercises by category
@app.route('/exercises/category/<category_name>', methods=['GET'])
def get_exercises_by_category(category_name):
    # Add logging to debug
    print(f"Received categoryName: {category_name}")
    filtered_exercises = [ex for ex in exercise if ex["categoryName"].lower() == category_name.lower()]
    return jsonify(filtered_exercises)

if __name__ == '__main__':
    app.run(debug=True)
