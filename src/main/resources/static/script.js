// Social Network Graph Analysis - Frontend JavaScript

// Split Pane Manager for resizable panels
class SplitPaneManager {
    constructor() {
        this.splitters = [];
        this.isDragging = false;
        this.currentSplitter = null;
        this.startPos = { x: 0, y: 0 };
        this.startSizes = {};

        this.init();
    }

    init() {
        this.setupSplitters();
        this.loadSavedSizes();
        this.setupEventListeners();
    }

    setupSplitters() {
        const splitters = document.querySelectorAll('.splitter');
        splitters.forEach(splitter => {
            this.splitters.push({
                element: splitter,
                direction: splitter.dataset.direction,
                target: splitter.dataset.target
            });
        });
    }

    setupEventListeners() {
        this.splitters.forEach(splitter => {
            splitter.element.addEventListener('mousedown', (e) => {
                this.startDrag(e, splitter);
            });
        });

        document.addEventListener('mousemove', (e) => {
            this.onDrag(e);
        });

        document.addEventListener('mouseup', () => {
            this.endDrag();
        });

        // Handle window resize
        window.addEventListener('resize', () => {
            this.handleWindowResize();
        });
    }

    startDrag(e, splitter) {
        e.preventDefault();
        this.isDragging = true;
        this.currentSplitter = splitter;

        this.startPos = {
            x: e.clientX,
            y: e.clientY
        };

        // Store initial sizes
        const targetPane = document.querySelector(`.${splitter.target}`);
        if (splitter.direction === 'vertical') {
            this.startSizes.width = targetPane.offsetWidth;
        } else {
            this.startSizes.height = targetPane.offsetHeight;
        }

        // Add dragging class for visual feedback
        document.body.classList.add('dragging');
        splitter.element.classList.add('dragging');

        // Disable text selection
        document.body.style.userSelect = 'none';
    }

    onDrag(e) {
        if (!this.isDragging || !this.currentSplitter) return;

        e.preventDefault();

        const splitter = this.currentSplitter;
        const targetPane = document.querySelector(`.${splitter.target}`);

        if (splitter.direction === 'vertical') {
            const deltaX = e.clientX - this.startPos.x;
            const newWidth = this.startSizes.width + deltaX;

            // Apply constraints
            const minWidth = this.getMinSize(targetPane, 'width');
            const maxWidth = this.getMaxSize(targetPane, 'width');
            const constrainedWidth = Math.max(minWidth, Math.min(maxWidth, newWidth));

            targetPane.style.flex = `0 0 ${constrainedWidth}px`;
        } else {
            const deltaY = e.clientY - this.startPos.y;
            const newHeight = this.startSizes.height + deltaY;

            // Apply constraints
            const minHeight = this.getMinSize(targetPane, 'height');
            const maxHeight = this.getMaxSize(targetPane, 'height');
            const constrainedHeight = Math.max(minHeight, Math.min(maxHeight, newHeight));

            targetPane.style.flex = `0 0 ${constrainedHeight}px`;
        }

        // Trigger graph resize if graph pane is affected
        this.triggerGraphResize();
    }

    endDrag() {
        if (!this.isDragging) return;

        this.isDragging = false;

        // Remove dragging classes
        document.body.classList.remove('dragging');
        if (this.currentSplitter) {
            this.currentSplitter.element.classList.remove('dragging');
        }

        // Re-enable text selection
        document.body.style.userSelect = '';

        // Save current sizes
        this.saveSizes();

        this.currentSplitter = null;
    }

    getMinSize(element, dimension) {
        const style = getComputedStyle(element);
        const property = dimension === 'width' ? 'minWidth' : 'minHeight';
        return parseInt(style[property]) || (dimension === 'width' ? 200 : 150);
    }

    getMaxSize(element, dimension) {
        const style = getComputedStyle(element);
        const property = dimension === 'width' ? 'maxWidth' : 'maxHeight';
        const maxValue = parseInt(style[property]);

        if (maxValue && maxValue !== 0) {
            return maxValue;
        }

        // Default max sizes based on viewport
        if (dimension === 'width') {
            return window.innerWidth * 0.6;
        } else {
            return window.innerHeight * 0.6;
        }
    }

    saveSizes() {
        const sizes = {};

        // Save all pane sizes
        const panes = ['left-pane', 'right-pane', 'bottom-pane'];
        panes.forEach(paneClass => {
            const pane = document.querySelector(`.${paneClass}`);
            if (pane) {
                const rect = pane.getBoundingClientRect();
                sizes[paneClass] = {
                    width: rect.width,
                    height: rect.height
                };
            }
        });

        localStorage.setItem('splitPaneSizes', JSON.stringify(sizes));
    }

    loadSavedSizes() {
        const savedSizes = localStorage.getItem('splitPaneSizes');
        if (!savedSizes) return;

        try {
            const sizes = JSON.parse(savedSizes);

            Object.keys(sizes).forEach(paneClass => {
                const pane = document.querySelector(`.${paneClass}`);
                if (pane && sizes[paneClass]) {
                    const { width, height } = sizes[paneClass];

                    if (paneClass === 'left-pane' || paneClass === 'right-pane') {
                        pane.style.flex = `0 0 ${width}px`;
                    } else if (paneClass === 'bottom-pane') {
                        pane.style.flex = `0 0 ${height}px`;
                    }
                }
            });
        } catch (error) {
            console.warn('Failed to load saved split pane sizes:', error);
        }
    }

    handleWindowResize() {
        // Adjust pane sizes if they exceed new window dimensions
        const panes = document.querySelectorAll('.split-pane');
        panes.forEach(pane => {
            const rect = pane.getBoundingClientRect();
            const maxWidth = window.innerWidth * 0.6;
            const maxHeight = window.innerHeight * 0.6;

            if (rect.width > maxWidth) {
                pane.style.flex = `0 0 ${maxWidth}px`;
            }
            if (rect.height > maxHeight) {
                pane.style.flex = `0 0 ${maxHeight}px`;
            }
        });

        this.triggerGraphResize();
    }

    triggerGraphResize() {
        // Notify the graph to resize after a short delay
        setTimeout(() => {
            if (window.app && window.app.handleGraphResize) {
                window.app.handleGraphResize();
            }
        }, 100);
    }
}

class SocialNetworkUI {
    constructor() {
        this.socialNetwork = new SocialNetworkAPI();
        this.graphData = { nodes: [], links: [] };
        this.selectedUser = null;
        this.showLabels = true;
        this.showCommunities = false;
        this.currentPath = [];
        this.cachedCommunities = [];
        this.splitPaneManager = null;

        this.initializeUI();
        this.setupEventListeners();
        this.initializeGraph();
        this.initializeSplitPanes();
        this.loadSampleData();
    }

    initializeUI() {
        // Theme toggle
        const themeToggle = document.getElementById('theme-toggle');
        const currentTheme = localStorage.getItem('theme') || 'light';
        document.documentElement.setAttribute('data-theme', currentTheme);
        this.updateThemeIcon(currentTheme);

        // Initialize tabs
        this.initializeTabs();
    }

    setupEventListeners() {
        // Theme toggle
        document.getElementById('theme-toggle').addEventListener('click', () => {
            this.toggleTheme();
        });

        // User management
        document.getElementById('add-user-btn').addEventListener('click', async () => {
            await this.addUser();
        });

        document.getElementById('add-friendship-btn').addEventListener('click', async () => {
            await this.addFriendship();
        });

        // Path finding
        document.getElementById('shortest-path-btn').addEventListener('click', async () => {
            await this.findPath('shortest');
        });

        document.getElementById('strongest-path-btn').addEventListener('click', async () => {
            await this.findPath('strongest');
        });

        document.getElementById('weakest-path-btn').addEventListener('click', async () => {
            await this.findPath('weakest');
        });

        // Graph controls
        document.getElementById('reset-zoom').addEventListener('click', () => {
            this.resetZoom();
        });

        document.getElementById('center-graph').addEventListener('click', () => {
            this.centerGraph();
        });

        document.getElementById('toggle-labels').addEventListener('click', () => {
            this.toggleLabels();
        });

        document.getElementById('toggle-communities').addEventListener('click', async () => {
            await this.toggleCommunities();
        });

        // Details panel
        document.getElementById('close-details').addEventListener('click', () => {
            this.closeDetailsPanel();
        });

        // Friend suggestions
        document.getElementById('get-suggestions-btn').addEventListener('click', async () => {
            await this.getFriendSuggestions();
        });

        // Export functionality
        document.getElementById('export-btn').addEventListener('click', async () => {
            await this.exportData();
        });

        // Enter key support for inputs
        document.getElementById('user-name').addEventListener('keypress', async (e) => {
            if (e.key === 'Enter') await this.addUser();
        });

        // Event delegation for suggestion buttons
        document.addEventListener('click', async (e) => {
            if (e.target.closest('.add-suggestion-btn')) {
                const button = e.target.closest('.add-suggestion-btn');
                const user1 = button.dataset.user1;
                const user2 = button.dataset.user2;
                await this.addSuggestedFriend(user1, user2);
            }
        });
    }

    initializeTabs() {
        const tabButtons = document.querySelectorAll('.tab-btn');
        const tabPanes = document.querySelectorAll('.tab-pane');

        tabButtons.forEach(button => {
            button.addEventListener('click', () => {
                const targetTab = button.getAttribute('data-tab');
                
                // Remove active class from all tabs and panes
                tabButtons.forEach(btn => btn.classList.remove('active'));
                tabPanes.forEach(pane => pane.classList.remove('active'));
                
                // Add active class to clicked tab and corresponding pane
                button.classList.add('active');
                document.getElementById(`${targetTab}-tab`).classList.add('active');
            });
        });
    }

    initializeGraph() {
        const svg = d3.select('#graph-svg');
        const container = document.getElementById('graph-svg-container');

        // Set up SVG dimensions
        this.updateGraphDimensions = () => {
            const rect = container.getBoundingClientRect();
            svg.attr('width', rect.width).attr('height', rect.height);
            this.width = rect.width;
            this.height = rect.height;

            // Update force simulation center
            if (this.simulation) {
                this.simulation.force('center', d3.forceCenter(this.width / 2, this.height / 2));
                this.simulation.alpha(0.3).restart();
            }
        };

        this.updateGraphDimensions();
        window.addEventListener('resize', this.updateGraphDimensions);

        // Create graph groups
        this.graphGroup = svg.append('g').attr('class', 'graph-group');
        this.linksGroup = this.graphGroup.append('g').attr('class', 'links');
        this.nodesGroup = this.graphGroup.append('g').attr('class', 'nodes');
        this.labelsGroup = this.graphGroup.append('g').attr('class', 'labels');

        // Set up zoom behavior
        this.zoom = d3.zoom()
            .scaleExtent([0.1, 4])
            .on('zoom', (event) => {
                this.graphGroup.attr('transform', event.transform);
            });

        svg.call(this.zoom);

        // Initialize force simulation
        this.simulation = d3.forceSimulation()
            .force('link', d3.forceLink().id(d => d.id).distance(100))
            .force('charge', d3.forceManyBody().strength(-300))
            .force('center', d3.forceCenter(this.width / 2, this.height / 2))
            .force('collision', d3.forceCollide().radius(30));
    }

    initializeSplitPanes() {
        // Initialize split pane manager
        this.splitPaneManager = new SplitPaneManager();

        // Make graph resize handler available globally
        window.app = this;
    }

    handleGraphResize() {
        // Update graph dimensions when split panes are resized
        if (this.updateGraphDimensions) {
            this.updateGraphDimensions();
        }
    }

    async loadSampleData() {
        // Sample data is already loaded by the backend
        await this.updateGraph();
        await this.updateStatistics();
        await this.updateUserSelects();
        await this.updateCommunities();
    }

    async addUser() {
        const userNameInput = document.getElementById('user-name');
        const userName = userNameInput.value.trim();

        if (!userName) {
            this.showToast('Please enter a username', 'warning');
            return;
        }

        if (await this.socialNetwork.hasUser(userName)) {
            this.showToast('User already exists', 'warning');
            return;
        }

        try {
            await this.socialNetwork.addUser(userName);
            userNameInput.value = '';

            await this.updateGraph();
            await this.updateStatistics();
            await this.updateUserSelects();
            this.showToast(`User "${userName}" added successfully`, 'success');
        } catch (error) {
            this.showToast('Error adding user', 'error');
        }
    }

    async addFriendship() {
        const user1 = document.getElementById('user1-select').value;
        const user2 = document.getElementById('user2-select').value;
        const weight = parseInt(document.getElementById('friendship-weight').value) || 5;

        if (!user1 || !user2) {
            this.showToast('Please select both users', 'warning');
            return;
        }

        if (user1 === user2) {
            this.showToast('Cannot create friendship with the same user', 'warning');
            return;
        }

        try {
            await this.socialNetwork.addFriendship(user1, user2, weight);

            await this.updateGraph();
            await this.updateStatistics();
            await this.updateCommunities();
            this.showToast(`Friendship created between ${user1} and ${user2}`, 'success');
        } catch (error) {
            this.showToast('Error creating friendship', 'error');
        }
    }

    async findPath(type) {
        const user1 = document.getElementById('path-user1').value;
        const user2 = document.getElementById('path-user2').value;

        if (!user1 || !user2) {
            this.showToast('Please select both users for path finding', 'warning');
            return;
        }

        try {
            let path;
            switch (type) {
                case 'shortest':
                    path = await this.socialNetwork.shortestPath(user1, user2);
                    break;
                case 'strongest':
                    path = await this.socialNetwork.findStrongestPath(user1, user2);
                    break;
                case 'weakest':
                    path = await this.socialNetwork.findWeakestPath(user1, user2);
                    break;
            }

            if (path && path.length > 0 && path[0] !== 'No path found') {
                this.highlightPath(path);
                this.showToast(`${type.charAt(0).toUpperCase() + type.slice(1)} path: ${path.join(' → ')}`, 'success');
            } else {
                this.showToast('No path found between the selected users', 'warning');
            }
        } catch (error) {
            this.showToast('Error finding path', 'error');
        }
    }

    async updateGraph() {
        try {
            this.graphData = await this.socialNetwork.getGraphData();
            // Update cached communities for color mapping
            if (this.showCommunities) {
                this.cachedCommunities = await this.socialNetwork.getCommunities();
            }
            this.renderGraph();
        } catch (error) {
            console.error('Error updating graph:', error);
        }
    }

    renderGraph() {
        if (!this.graphData || !this.graphData.nodes || !this.graphData.links) {
            console.warn('Graph data not available');
            return;
        }

        // Update links
        const links = this.linksGroup.selectAll('.link')
            .data(this.graphData.links, d => `${d.source.id || d.source}-${d.target.id || d.target}`);

        links.exit().remove();

        const linksEnter = links.enter().append('line')
            .attr('class', 'link')
            .attr('stroke-width', d => Math.sqrt(d.weight || 1) * 2);

        const linksUpdate = linksEnter.merge(links);

        // Update nodes
        const nodes = this.nodesGroup.selectAll('.node')
            .data(this.graphData.nodes, d => d.id);

        nodes.exit().remove();

        const nodesEnter = nodes.enter().append('circle')
            .attr('class', 'node')
            .attr('r', 15)
            .attr('fill', this.getNodeColor.bind(this))
            .call(this.drag());

        nodesEnter.on('click', (event, d) => {
            this.selectUser(d.id);
        });

        const nodesUpdate = nodesEnter.merge(nodes)
            .attr('fill', this.getNodeColor.bind(this)); // Update colors on re-render

        // Update labels
        const labels = this.labelsGroup.selectAll('.node-label')
            .data(this.graphData.nodes, d => d.id);

        labels.exit().remove();

        const labelsEnter = labels.enter().append('text')
            .attr('class', 'node-label')
            .text(d => d.id)
            .style('display', this.showLabels ? 'block' : 'none');

        const labelsUpdate = labelsEnter.merge(labels)
            .style('display', this.showLabels ? 'block' : 'none');

        // Update simulation
        this.simulation.nodes(this.graphData.nodes);
        this.simulation.force('link').links(this.graphData.links);

        this.simulation.on('tick', () => {
            linksUpdate
                .attr('x1', d => d.source.x)
                .attr('y1', d => d.source.y)
                .attr('x2', d => d.target.x)
                .attr('y2', d => d.target.y);

            nodesUpdate
                .attr('cx', d => d.x)
                .attr('cy', d => d.y);

            labelsUpdate
                .attr('x', d => d.x)
                .attr('y', d => d.y + 5);
        });

        this.simulation.alpha(1).restart();
    }

    getNodeColor(d) {
        // Define community colors
        const communityColors = [
            '#3b82f6', '#ef4444', '#10b981', '#f59e0b', '#8b5cf6',
            '#06b6d4', '#f97316', '#84cc16', '#ec4899', '#6b7280'
        ];

        if (this.showCommunities) {
            // Find which community this user belongs to
            const communities = this.cachedCommunities || [];
            for (let i = 0; i < communities.length; i++) {
                if (communities[i].includes(d.id)) {
                    return communityColors[i % communityColors.length];
                }
            }
        }

        // Default colors
        if (this.selectedUser === d.id) {
            return '#f59e0b'; // Accent color
        }
        return '#3b82f6'; // Primary color
    }

    drag() {
        return d3.drag()
            .on('start', (event, d) => {
                if (!event.active) this.simulation.alphaTarget(0.3).restart();
                d.fx = d.x;
                d.fy = d.y;
            })
            .on('drag', (event, d) => {
                d.fx = event.x;
                d.fy = event.y;
            })
            .on('end', (event, d) => {
                if (!event.active) this.simulation.alphaTarget(0);
                d.fx = null;
                d.fy = null;
            });
    }

    selectUser(userId) {
        this.selectedUser = userId;
        this.updateUserDetails(userId);
        this.renderGraph(); // Re-render to update colors
    }

    async updateUserDetails(userId) {
        const detailsContent = document.getElementById('user-details-content');

        try {
            const userStats = await this.socialNetwork.getUserStats(userId);

            if (userStats) {
                detailsContent.innerHTML = `
                    <div class="user-profile">
                        <h4>${userId}</h4>
                        <div class="user-stats">
                            <div class="stat-row">
                                <span>Connections:</span>
                                <span>${userStats.connections}</span>
                            </div>
                            <div class="stat-row">
                                <span>Degree Centrality:</span>
                                <span>${userStats.degreeCentrality}</span>
                            </div>
                            <div class="stat-row">
                                <span>Closeness Centrality:</span>
                                <span>${userStats.closenessCentrality.toFixed(3)}</span>
                            </div>
                            <div class="stat-row">
                                <span>PageRank:</span>
                                <span>${userStats.pageRank.toFixed(3)}</span>
                            </div>
                            <div class="stat-row">
                                <span>Clustering Coefficient:</span>
                                <span>${userStats.clusteringCoefficient.toFixed(3)}</span>
                            </div>
                        </div>
                        <div class="user-friends">
                            <h5>Friends:</h5>
                            <div class="friends-list">
                                ${userStats.friends.map(friend => `<span class="friend-tag">${friend}</span>`).join('')}
                            </div>
                        </div>
                    </div>
                `;
            }
        } catch (error) {
            detailsContent.innerHTML = '<p class="error">Error loading user details</p>';
        }
    }

    async updateStatistics() {
        try {
            const stats = await this.socialNetwork.getNetworkStats();

            document.getElementById('total-users').textContent = stats.totalUsers || 0;
            document.getElementById('total-connections').textContent = stats.totalConnections || 0;
            document.getElementById('total-communities').textContent = stats.totalCommunities || 0;
            document.getElementById('network-density').textContent = (stats.networkDensity || 0).toFixed(3);
            document.getElementById('avg-clustering').textContent = (stats.avgClustering || 0).toFixed(3);

            document.getElementById('most-connected').textContent = stats.mostConnected || '-';
            document.getElementById('most-influential').textContent = stats.mostInfluential || '-';
            document.getElementById('highest-closeness').textContent = stats.highestCloseness || '-';
        } catch (error) {
            console.error('Error updating statistics:', error);
        }
    }

    async updateUserSelects() {
        try {
            const users = await this.socialNetwork.getAllUsers();
            const selects = [
                'user1-select', 'user2-select',
                'path-user1', 'path-user2', 'suggestions-user'
            ];

            selects.forEach(selectId => {
                const select = document.getElementById(selectId);
                const currentValue = select.value;

                // Clear existing options except the first one
                while (select.children.length > 1) {
                    select.removeChild(select.lastChild);
                }

                // Add user options
                users.forEach(user => {
                    const option = document.createElement('option');
                    option.value = user;
                    option.textContent = user;
                    select.appendChild(option);
                });

                // Restore previous selection if still valid
                if (users.includes(currentValue)) {
                    select.value = currentValue;
                }
            });
        } catch (error) {
            console.error('Error updating user selects:', error);
        }
    }

    async updateCommunities() {
        try {
            const communities = await this.socialNetwork.getCommunities();
            this.cachedCommunities = communities; // Cache for color mapping
            const communitiesList = document.getElementById('communities-list');

            const communityColors = [
                '#3b82f6', '#ef4444', '#10b981', '#f59e0b', '#8b5cf6',
                '#06b6d4', '#f97316', '#84cc16', '#ec4899', '#6b7280'
            ];

            communitiesList.innerHTML = communities.map((community, index) => `
                <div class="community-item">
                    <div class="community-header">
                        <span class="community-color" style="background: ${communityColors[index % communityColors.length]}"></span>
                        <span class="community-title">Community ${index + 1}</span>
                        <span class="community-size">${community.length} members</span>
                    </div>
                    <div class="community-members">
                        ${community.map(member => `<span class="member-tag">${member}</span>`).join('')}
                    </div>
                </div>
            `).join('');
        } catch (error) {
            console.error('Error updating communities:', error);
        }
    }

    async getFriendSuggestions() {
        const user = document.getElementById('suggestions-user').value;
        if (!user) {
            this.showToast('Please select a user', 'warning');
            return;
        }

        try {
            const suggestions = await this.socialNetwork.suggestFriends(user);
            const suggestionsList = document.getElementById('suggestions-list');

            if (suggestions.length === 0) {
                suggestionsList.innerHTML = '<p class="no-suggestions">No friend suggestions available</p>';
            } else {
                suggestionsList.innerHTML = `
                    <div class="suggestions-header">
                        <h4>Friend Suggestions for ${user}</h4>
                    </div>
                    <div class="suggestions-grid">
                        ${suggestions.map(suggestion => `
                            <div class="suggestion-item">
                                <span class="suggestion-name">${suggestion}</span>
                                <button class="btn btn-sm btn-primary add-suggestion-btn" data-user1="${user}" data-user2="${suggestion}">
                                    <i class="fas fa-plus"></i> Add
                                </button>
                            </div>
                        `).join('')}
                    </div>
                `;
            }
        } catch (error) {
            this.showToast('Error fetching suggestions', 'error');
        }
    }

    async addSuggestedFriend(user1, user2) {
        try {
            await this.socialNetwork.addFriendship(user1, user2, 3); // Default weight for suggestions
            await this.updateGraph();
            await this.updateStatistics();
            await this.updateCommunities();
            await this.getFriendSuggestions(); // Refresh suggestions
            this.showToast(`Friendship created between ${user1} and ${user2}`, 'success');
        } catch (error) {
            this.showToast('Error creating friendship', 'error');
        }
    }

    highlightPath(path) {
        this.currentPath = path;
        
        // Reset all link styles
        this.linksGroup.selectAll('.link').classed('highlighted', false);
        
        // Highlight path links
        for (let i = 0; i < path.length - 1; i++) {
            const source = path[i];
            const target = path[i + 1];
            
            this.linksGroup.selectAll('.link')
                .filter(d => {
                    const sourceId = d.source.id || d.source;
                    const targetId = d.target.id || d.target;
                    return (sourceId === source && targetId === target) || 
                           (sourceId === target && targetId === source);
                })
                .classed('highlighted', true);
        }
    }

    toggleTheme() {
        const currentTheme = document.documentElement.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        
        document.documentElement.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        this.updateThemeIcon(newTheme);
    }

    updateThemeIcon(theme) {
        const icon = document.querySelector('#theme-toggle i');
        icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
    }

    toggleLabels() {
        this.showLabels = !this.showLabels;
        this.labelsGroup.selectAll('.node-label')
            .style('display', this.showLabels ? 'block' : 'none');
    }

    async toggleCommunities() {
        this.showCommunities = !this.showCommunities;
        if (this.showCommunities && (!this.cachedCommunities || this.cachedCommunities.length === 0)) {
            this.cachedCommunities = await this.socialNetwork.getCommunities();
        }
        this.renderGraph();
    }

    resetZoom() {
        const svg = d3.select('#graph-svg');
        svg.transition().duration(750).call(
            this.zoom.transform,
            d3.zoomIdentity
        );
    }

    centerGraph() {
        const svg = d3.select('#graph-svg');
        const bounds = this.graphGroup.node().getBBox();
        const fullWidth = this.width;
        const fullHeight = this.height;
        const width = bounds.width;
        const height = bounds.height;
        const midX = bounds.x + width / 2;
        const midY = bounds.y + height / 2;
        
        if (width === 0 || height === 0) return;
        
        const scale = Math.min(fullWidth / width, fullHeight / height) * 0.8;
        const translate = [fullWidth / 2 - scale * midX, fullHeight / 2 - scale * midY];
        
        svg.transition().duration(750).call(
            this.zoom.transform,
            d3.zoomIdentity.translate(translate[0], translate[1]).scale(scale)
        );
    }

    closeDetailsPanel() {
        this.selectedUser = null;
        document.getElementById('user-details-content').innerHTML = 
            '<p class="no-selection">Click on a user to view details</p>';
        this.renderGraph();
    }

    async exportData() {
        try {
            const data = {
                users: await this.socialNetwork.getAllUsers(),
                graphData: await this.socialNetwork.getGraphData(),
                statistics: await this.socialNetwork.getNetworkStats(),
                communities: await this.socialNetwork.getCommunities()
            };

            const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'social-network-data.json';
            a.click();
            URL.revokeObjectURL(url);

            this.showToast('Data exported successfully', 'success');
        } catch (error) {
            this.showToast('Error exporting data', 'error');
        }
    }

    showToast(message, type = 'info') {
        const toastContainer = document.getElementById('toast-container');
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.innerHTML = `
            <div class="toast-content">
                <span>${message}</span>
                <button class="toast-close" onclick="this.parentElement.parentElement.remove()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        `;
        
        toastContainer.appendChild(toast);
        
        // Auto remove after 5 seconds
        setTimeout(() => {
            if (toast.parentElement) {
                toast.remove();
            }
        }, 5000);
    }
}

// Real Social Network API that communicates with the Java backend
class SocialNetworkAPI {
    constructor() {
        this.baseUrl = '/api';
    }

    async addUser(name) {
        try {
            const response = await fetch(`${this.baseUrl}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name: name })
            });
            return await response.json();
        } catch (error) {
            console.error('Error adding user:', error);
            throw error;
        }
    }

    async hasUser(name) {
        try {
            const users = await this.getAllUsers();
            return users.includes(name);
        } catch (error) {
            console.error('Error checking user:', error);
            return false;
        }
    }

    async addFriendship(user1, user2, weight) {
        try {
            const response = await fetch(`${this.baseUrl}/friendships`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ user1, user2, weight })
            });
            return await response.json();
        } catch (error) {
            console.error('Error adding friendship:', error);
            throw error;
        }
    }

    async getAllUsers() {
        try {
            const response = await fetch(`${this.baseUrl}/users`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching users:', error);
            return [];
        }
    }

    async getGraphData() {
        try {
            const response = await fetch(`${this.baseUrl}/graph`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching graph data:', error);
            return { nodes: [], links: [] };
        }
    }

    async getUserStats(userId) {
        try {
            const response = await fetch(`${this.baseUrl}/users/${userId}/stats`);
            if (!response.ok) return null;
            return await response.json();
        } catch (error) {
            console.error('Error fetching user stats:', error);
            return null;
        }
    }

    async getNetworkStats() {
        try {
            const response = await fetch(`${this.baseUrl}/stats`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching network stats:', error);
            return {};
        }
    }

    async getCommunities() {
        try {
            const response = await fetch(`${this.baseUrl}/communities`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching communities:', error);
            return [];
        }
    }

    async getCommunity(userId) {
        try {
            const communities = await this.getCommunities();
            return communities.find(community => community.includes(userId)) || [];
        } catch (error) {
            console.error('Error fetching user community:', error);
            return [];
        }
    }

    async shortestPath(user1, user2) {
        try {
            const response = await fetch(`${this.baseUrl}/path/${user1}/${user2}?type=shortest`);
            const result = await response.json();
            return result.path || ['No path found'];
        } catch (error) {
            console.error('Error finding shortest path:', error);
            return ['No path found'];
        }
    }

    async findStrongestPath(user1, user2) {
        try {
            const response = await fetch(`${this.baseUrl}/path/${user1}/${user2}?type=strongest`);
            const result = await response.json();
            return result.path || ['No path found'];
        } catch (error) {
            console.error('Error finding strongest path:', error);
            return ['No path found'];
        }
    }

    async findWeakestPath(user1, user2) {
        try {
            const response = await fetch(`${this.baseUrl}/path/${user1}/${user2}?type=weakest`);
            const result = await response.json();
            return result.path || ['No path found'];
        } catch (error) {
            console.error('Error finding weakest path:', error);
            return ['No path found'];
        }
    }

    async suggestFriends(userId) {
        try {
            const response = await fetch(`${this.baseUrl}/users/${userId}/suggestions`);
            if (!response.ok) return [];
            return await response.json();
        } catch (error) {
            console.error('Error fetching friend suggestions:', error);
            return [];
        }
    }

    async getMutualFriends(user1, user2) {
        try {
            const response = await fetch(`${this.baseUrl}/users/${user1}/mutual/${user2}`);
            if (!response.ok) return [];
            return await response.json();
        } catch (error) {
            console.error('Error fetching mutual friends:', error);
            return [];
        }
    }
}

// Initialize the application
let app;
document.addEventListener('DOMContentLoaded', () => {
    app = new SocialNetworkUI();
});
