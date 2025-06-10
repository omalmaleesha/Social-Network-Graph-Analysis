# 🎨 Social Network Graph Analysis - Interactive Web UI

This project now includes a beautiful, modern web-based user interface for the Social Network Graph Analysis tool. The UI provides an interactive way to visualize and analyze social networks with real-time graph visualization, comprehensive analytics, and user-friendly controls.

## 🌟 New UI Features

### 📊 Interactive Graph Visualization
- **Real-time network visualization** using D3.js
- **Interactive nodes and edges** with zoom, pan, and drag functionality
- **Community highlighting** with different colors for each community
- **Path visualization** with animated highlighting for shortest/strongest/weakest paths
- **Responsive design** that works on desktop and mobile devices

### 🎛️ Control Panel
- **User Management**: Add new users with a simple form
- **Friendship Creation**: Create weighted connections between users
- **Path Finding**: Find shortest, strongest, and weakest paths between users
- **Theme Toggle**: Switch between light and dark themes
- **Export Functionality**: Download network data as JSON

### 📈 Analytics Dashboard
- **Real-time Statistics**: Network density, clustering coefficient, total users/connections
- **Top Users**: Most connected, most influential, highest closeness centrality
- **Community Detection**: Visual representation of detected communities
- **Friend Suggestions**: AI-powered friend recommendations
- **User Details Panel**: Detailed statistics for individual users

### 🎨 Modern Design
- **Clean, professional interface** with smooth animations
- **Dark/Light theme support** with persistent user preference
- **Responsive layout** that adapts to different screen sizes
- **Toast notifications** for user feedback
- **Loading states** for better user experience

## 🚀 How to Run the Web UI

### Prerequisites
- Java 22 or higher
- Maven 3.6 or higher

### Steps to Launch

1. **Navigate to the project directory**:
   ```bash
   cd "Social-Network-Graph-Analysis"
   ```

2. **Build and run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Open your web browser** and navigate to:
   ```
   http://localhost:8080
   ```

4. **Start exploring!** The application comes pre-loaded with sample data to demonstrate all features.

### Alternative: Run with Maven Wrapper
If you have the Maven wrapper in your project:
```bash
./mvnw spring-boot:run    # On Linux/Mac
mvnw.cmd spring-boot:run  # On Windows
```

## 🎮 How to Use the UI

### Adding Users and Connections
1. **Add a new user**: Enter a username in the "Add User" field and click the + button
2. **Create friendships**: Select two users from the dropdowns, set a weight (1-10), and click the link button
3. **Watch the graph update** in real-time as you add connections

### Exploring the Network
1. **Click on any node** to view detailed user statistics in the right panel
2. **Drag nodes** to rearrange the graph layout
3. **Use mouse wheel** to zoom in/out
4. **Toggle labels** to show/hide user names
5. **Highlight communities** to see different groups in different colors

### Finding Paths
1. **Select two users** from the path finding dropdowns
2. **Choose path type**: Shortest (fewest hops), Strongest (highest weights), or Weakest (lowest weights)
3. **Watch the path highlight** on the graph with animation

### Analyzing Communities
1. **View the Communities tab** at the bottom to see all detected communities
2. **Each community** is color-coded and shows member count
3. **Community detection** uses Disjoint Set Union algorithm

### Getting Friend Suggestions
1. **Go to the Friend Suggestions tab**
2. **Select a user** from the dropdown
3. **View AI-powered suggestions** based on mutual friends and network analysis
4. **Add suggested friends** with one click

## 🛠️ Technical Architecture

### Frontend Stack
- **HTML5** with semantic markup
- **CSS3** with CSS Grid, Flexbox, and CSS Variables for theming
- **Vanilla JavaScript** with ES6+ features and async/await
- **D3.js v7** for interactive graph visualization
- **Font Awesome** for icons

### Backend Integration
- **Spring Boot** REST API serving the frontend
- **JSON endpoints** for all data exchange
- **Real-time updates** through API calls
- **Static file serving** for the web assets

### Key Components
- **SocialNetworkUI**: Main frontend controller class
- **SocialNetworkAPI**: API client for backend communication
- **Graph Visualization**: D3.js-based interactive network renderer
- **Theme System**: CSS variables-based dark/light theme support
- **Responsive Layout**: Mobile-first design with breakpoints

## 🎯 API Endpoints

The UI communicates with these REST endpoints:

- `GET /api/users` - Get all users
- `POST /api/users` - Add a new user
- `POST /api/friendships` - Create a friendship
- `GET /api/graph` - Get graph data (nodes and links)
- `GET /api/stats` - Get network statistics
- `GET /api/users/{id}/stats` - Get user-specific statistics
- `GET /api/communities` - Get detected communities
- `GET /api/users/{id}/suggestions` - Get friend suggestions
- `GET /api/path/{user1}/{user2}?type={type}` - Find paths between users

## 🎨 Customization

### Themes
The UI supports custom themes through CSS variables. You can modify colors in `styles.css`:

```css
:root {
    --primary-color: #3b82f6;    /* Main brand color */
    --accent-color: #f59e0b;     /* Accent/highlight color */
    --success-color: #10b981;    /* Success states */
    /* ... more variables */
}
```

### Graph Appearance
Customize the graph visualization by modifying the D3.js settings in `script.js`:

```javascript
// Force simulation parameters
.force('charge', d3.forceManyBody().strength(-300))  // Node repulsion
.force('link', d3.forceLink().distance(100))         // Link distance
.force('collision', d3.forceCollide().radius(30))    // Collision detection
```

## 🔧 Troubleshooting

### Common Issues

1. **Port 8080 already in use**:
   - Change the port in `application.properties`: `server.port=8081`
   - Or kill the process using port 8080

2. **Graph not displaying**:
   - Check browser console for JavaScript errors
   - Ensure D3.js is loading properly
   - Verify API endpoints are responding

3. **Styling issues**:
   - Clear browser cache
   - Check if CSS files are loading
   - Verify CSS variables are supported (modern browsers only)

### Browser Compatibility
- **Recommended**: Chrome 90+, Firefox 88+, Safari 14+, Edge 90+
- **Required features**: CSS Grid, CSS Variables, ES6+ JavaScript, Fetch API

## 🚀 Future Enhancements

### Planned Features
- **Real-time collaboration** with WebSocket support
- **Graph algorithms visualization** with step-by-step animation
- **Advanced filtering** and search capabilities
- **Data import/export** in multiple formats (CSV, GraphML, etc.)
- **Performance optimization** for large networks (1000+ nodes)
- **Mobile app** using React Native or Flutter
- **3D graph visualization** using Three.js

### Contributing
The UI is built with modularity in mind. To add new features:

1. **Frontend**: Add new components in `script.js` and styles in `styles.css`
2. **Backend**: Add new endpoints in `SocialNetworkController.java`
3. **Integration**: Update the `SocialNetworkAPI` class for new endpoints

## 📜 License

This enhanced UI maintains the same open-source license as the original project and is free to use for learning and educational purposes.

---

**Enjoy exploring your social networks with this beautiful, interactive interface! 🎉**
