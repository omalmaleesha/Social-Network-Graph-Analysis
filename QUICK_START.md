# 🚀 Quick Start Guide - Social Network Graph Analysis UI

## 🎯 Immediate Testing (No Setup Required)

### Option 1: Test UI with Mock Data
1. **Open the test file**: Double-click `test-ui.html` in your browser
2. **Explore the interface**: The UI loads with sample data (Alice, Bob, Charlie, David, Eve)
3. **Test all features**: All UI functions work with mock backend

### Option 2: Run Full Application (Requires Maven)
1. **Install Maven**: Download from https://maven.apache.org/download.cgi
2. **Run the application**: Execute `mvn spring-boot:run` in the project directory
3. **Open browser**: Navigate to `http://localhost:8080`

### Option 3: Use IDE (Recommended for Development)
1. **Open in IntelliJ IDEA**: Import the project as a Maven project
2. **Run the main class**: Execute `SocialNetworkApplication.java`
3. **Access the UI**: Browser opens automatically to `http://localhost:8080`

## 🎮 Testing Checklist - 5 Minutes

### ✅ Basic Functions (2 minutes)
1. **Add a user**: Type your name in "Add User" field and click +
2. **Create friendship**: Select two users, set weight 5, click link button
3. **Find path**: Select two users, click "Shortest" path button
4. **View details**: Click on any node to see user statistics

### ✅ Advanced Features (3 minutes)
1. **Toggle communities**: Click the palette button to see community colors
2. **Toggle theme**: Click moon/sun icon to switch dark/light mode
3. **Get suggestions**: Go to Friend Suggestions tab, select user, click "Get Suggestions"
4. **Export data**: Click Export button to download network data

## 🎨 UI Features Demonstration

### 📊 Graph Visualization
- **Zoom**: Mouse wheel to zoom in/out
- **Pan**: Click and drag empty space to move graph
- **Drag nodes**: Click and drag any user node to reposition
- **Select users**: Click nodes to see detailed statistics

### 🎛️ Control Panel
- **Add User**: Enter username and click + button
- **Add Friendship**: Select users, set weight (1-10), click link button
- **Find Paths**: Choose users and path type (shortest/strongest/weakest)

### 📈 Analytics Dashboard
- **Real-time Stats**: Watch numbers update as you add users/connections
- **User Details**: Click nodes to see centrality measures and friend lists
- **Communities**: View detected communities with member lists

### 🔍 Advanced Analysis
- **Path Finding**: Visualize shortest, strongest, and weakest paths
- **Community Detection**: See friend groups highlighted in different colors
- **Friend Suggestions**: Get AI-powered recommendations based on mutual friends

## 🧪 Test Scenarios

### Scenario 1: Building Your Network (2 minutes)
1. Add yourself as a user
2. Add 3-4 friends
3. Connect yourself to your friends
4. Connect some friends to each other
5. Watch the network statistics update

### Scenario 2: Path Analysis (1 minute)
1. Select two users who aren't directly connected
2. Find the shortest path between them
3. Try strongest and weakest paths
4. Observe the highlighted paths on the graph

### Scenario 3: Community Formation (2 minutes)
1. Create two separate groups of friends
2. Toggle community highlighting to see the groups
3. Connect the groups with one friendship
4. Watch how communities merge

## 🎯 Expected Results

### ✅ What You Should See:
- **Interactive graph** with draggable, clickable nodes
- **Real-time updates** when adding users or friendships
- **Path highlighting** when finding routes between users
- **Community colors** when toggling community view
- **Detailed statistics** in the sidebar and user details panel
- **Toast notifications** for all user actions
- **Smooth animations** and responsive design

### ✅ Performance Expectations:
- **Instant response** for user interactions
- **Smooth animations** for graph updates
- **Fast path finding** even with 20+ users
- **Responsive design** that works on mobile devices

## 🔧 Troubleshooting

### Common Issues:

#### Graph Not Displaying
- **Check browser console** for JavaScript errors
- **Ensure D3.js loads** from CDN (requires internet connection)
- **Try refreshing** the page

#### Backend Connection Issues (Full App)
- **Verify Maven is installed**: Run `mvn --version`
- **Check port 8080** is not in use by another application
- **Look for Java errors** in the console output

#### Styling Issues
- **Clear browser cache** (Ctrl+F5 or Cmd+Shift+R)
- **Check CSS file loads** in browser developer tools
- **Verify modern browser** (Chrome 90+, Firefox 88+, Safari 14+)

### Quick Fixes:
1. **Refresh the page** - Solves most temporary issues
2. **Clear browser cache** - Fixes CSS/JS loading problems
3. **Check browser console** - Shows detailed error messages
4. **Try test-ui.html** - Works without backend dependencies

## 📱 Mobile Testing

### Mobile-Friendly Features:
- **Responsive layout** adapts to small screens
- **Touch-friendly** buttons and controls
- **Swipe gestures** for graph navigation
- **Collapsible panels** for better mobile experience

### Mobile Test Steps:
1. Open the UI on your mobile device
2. Test touch interactions with the graph
3. Verify all buttons are easily tappable
4. Check that panels stack properly on small screens

## 🎉 Success Indicators

### ✅ You'll Know It's Working When:
- Graph displays with sample users (Alice, Bob, Charlie, David, Eve)
- You can add new users and they appear in dropdowns
- Friendships create visible links between nodes
- Path finding highlights routes on the graph
- Statistics update in real-time
- Theme toggle changes the entire interface
- Export downloads a JSON file with network data

### 🚀 Ready to Explore!

The Social Network Graph Analysis UI is designed to be intuitive and engaging. Spend a few minutes exploring the features, and you'll quickly see how powerful this tool is for understanding social network structures and relationships.

**Enjoy analyzing your social networks!** 🎊
