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
├── src/main/java/edu/dsa/
│   ├── model/
│   │   └── User.java                    # User data model
│   ├── service/
│   │   ├── SocialNetwork.java           # Core network analysis logic
│   │   └── DSU.java                     # Disjoint Set Union for communities
│   ├── controller/
│   │   └── SocialNetworkController.java # REST API endpoints
│   ├── config/
│   │   └── WebConfig.java               # Web configuration
│   ├── Main.java                        # Original console application
│   └── SocialNetworkApplication.java    # Spring Boot web application
├── src/main/resources/
│   ├── static/
│   │   ├── index.html                   # Main web interface
│   │   ├── styles.css                   # Modern CSS styling
│   │   └── script.js                    # Interactive JavaScript
│   └── application.properties           # Spring Boot configuration
├── run-ui.bat                           # Windows launcher script
├── run-ui.sh                            # Unix launcher script
├── pom.xml                              # Maven dependencies
├── README.md                            # This file
└── UI_README.md                         # Detailed UI documentation
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

## 🎨 NEW: Interactive Web UI

**🚀 We've added a beautiful, modern web-based user interface!**

### Features:
- **Interactive Graph Visualization** with D3.js
- **Real-time Network Analysis** and statistics
- **Community Detection** with color-coded visualization
- **Path Finding** with animated highlighting
- **Friend Suggestions** powered by AI algorithms
- **Dark/Light Theme** support
- **Responsive Design** for mobile and desktop
- **Export Functionality** for network data

### How to Run the Web UI:
1. **Quick Start**: Double-click `run-ui.bat` (Windows) or `run-ui.sh` (Linux/Mac)
2. **With Maven**: Run `mvn spring-boot:run`
3. **With IDE**: Open `SocialNetworkApplication.java` and run it
4. **Open Browser**: Navigate to `http://localhost:8080`

📖 **Detailed UI Documentation**: See [UI_README.md](UI_README.md) for complete instructions and features.

## 🧱 Future Enhancements

- ✅ ~~GUI or Web interface~~ **COMPLETED!**
- Add user profile data (e.g., interests, location)
- Real-time collaboration with WebSocket support
- Persistent storage using file or database
- 3D graph visualization using Three.js
- Mobile app development
- Advanced filtering and search capabilities

---

## 📜 License

This project is open source and free to use for learning purposes.

---

## 🤝 Contributing

Contributions are welcome! Fork the repo and submit a pull request with enhancements or bug fixes.