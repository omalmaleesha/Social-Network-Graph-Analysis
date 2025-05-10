# 🧠 Social Network Graph Analysis (Java DSA Project)

This project is a **Social Network Graph Analysis** tool built in **Java** using **core Data Structures and Algorithms (DSA)**. It simulates a social media-like environment where users can connect, and the system analyzes friendships, suggests new friends, finds paths between users, detects communities, and measures user influence.

---

## 🚀 Features

- 👥 Add Users and Friendships (with weights)
- 🔁 Mutual Friends Finder
- 🤝 Enhanced Friend Suggestion System (using Jaccard Similarity)
- 🧭 Shortest Path Between Two Users (BFS)
- 💪 Strongest and Weakest Path Finder (Dijkstra's Algorithm)
- 🧩 Community Detection (Disjoint Set Union)
- 📊 Centrality Measures (Degree and Closeness Centrality)
- 🌟 PageRank for User Influence Ranking
- 👑 Identification of Most Connected and Influential Users

---

## 📚 Technologies & DSA Concepts Used

- Java (No external libraries)
- HashMap & HashSet (for Graph, User, and PageRank management)
- Adjacency List (for graph representation)
- Breadth-First Search (for shortest path)
- Dijkstra's Algorithm (for strongest/weakest paths)
- Disjoint Set Union (for community detection)
- Priority Queue (for path finding and centrality measures)
- Iterative Algorithms (for PageRank computation)
- Set Operations (for Jaccard Similarity in friend suggestions)
- Basic Object-Oriented Programming

---

## 🛠️ Project Structure

```
├── model/
│   └── User.java
├── service/
│   ├── SocialNetwork.java
│   └── DSU.java
├── Main.java
└── README.md
```

---

## 🔧 How to Run

1. Clone or download the repository.
2. Open it in **IntelliJ IDEA** or any Java IDE.
3. Compile and run `Main.java` to test the project.

---

## 🧪 Sample Output

```
Mutual Friends (Alice & Bob): [Charlie, David]
Friend Suggestions for Alice: [Eve]
Shortest Path Alice -> Eve: [Alice, Bob, David, Eve]
Strongest Path Alice -> Eve: [Alice, Bob, David, Eve]
Weakest Path Alice -> Eve: [Alice, Charlie, David, Eve]
Number of Communities: 2
Communities: {David=[Alice, Bob, Charlie, David, Eve], Grace=[Frank, Grace, Heidi]}
--- Centrality Measures ---
Degree Centrality of Alice: 2
Degree Centrality of David: 3
Most Connected User: David
Closeness Centrality of Alice: 0.5714285714285714
Closeness Centrality of David: 0.8333333333333334
User with Highest Closeness Centrality: David
--- New Features: PageRank and Friend Suggestions ---
PageRank of Alice: 0.142857
PageRank of David: 0.238095
Most Influential User: David
Enhanced Friend Suggestions for Alice: [Eve]
Enhanced Friend Suggestions for Frank: [Heidi]
```

---

## 🧱 Future Enhancements

- GUI or Web interface (JavaFX or Spring Boot) for interactive network visualization
- Add user profile data (e.g., interests, location)
- Real-time friend graph visualization using graph libraries
- Persistent storage using file or database
- Advanced centrality measures (e.g., Betweenness Centrality)
- Detection of bridges and articulation points in the network

---

## 📜 License

This project is open source and free to use for learning purposes.

---

## 🤝 Contributing

Contributions are welcome! Fork the repo and submit a pull request with enhancements or bug fixes.