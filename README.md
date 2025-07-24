# 🏝️ Island Simulation

This is a multithreaded Java simulation of an island ecosystem. The simulation models different entities such as animals and plants, their movement, eating behavior, reproduction, and interaction on a 2D grid representing the island.

## 📦 Features

- 🌿 Multiple types of animals and plants with custom behavior and limits
- 🧠 Multithreaded logic using `ExecutorService` for concurrent actions
- 🔄 Movement, eating, and reproduction cycles
- 📊 Console visualization of the island and entity population
- ✅ JSON-based configuration of entity parameters

## 🔧 Technologies

- Java 17+
- Concurrency: `ExecutorService`, `Future`, `Locks`
- JSON parsing
- OOP principles (encapsulation, inheritance, polymorphism)
- Reflection API


## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/island-simulation.git
   cd island-simulation
Run the simulation from your IDE or with:


javac -d out src/**/*.java
java -cp out Main

Modify entity parameters in the JSON files if needed.

📌 Notes

Each simulation step is executed in parallel, but results are synchronized before rendering.

Simulation continues while there are living animals (excluding plants).

You can adjust island size in the Field class.

🧑‍💻 Author

Eriks Larionovs

Created as a learning and demonstration project for OOP and Java concurrency.


