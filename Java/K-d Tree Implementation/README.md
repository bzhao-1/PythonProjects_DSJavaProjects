# A BST K-d tree implementation (where k = 2)
# Ben Zhao 
4 March 2022 

Implementation of a BST for 2-dimensional data. A K-d tree is a BST which uses 2D points as keys(k=2). 
Our programs supports efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point). In addition to typical BST methods like put, get, and contains. 
Each node contains an x and y coordinate. The root node and every other level thereafter are x-based nodes. The children of the root node and every other level thereafter are y-based nodes. When entering a new node compare x-coordinate to determine whether left or child of x-based nodes and compare y-coordinate to determine whether left or right of y based nodes

Node to be put in (3,5)
Example: Root   (2,3) x based node, compare x node of node to put in to root node to determine whether it will be a left or right child: 3 > 2 so will be right child
                /   \
Level 1     (0,1)   (3,4) y based nodes, compare this y node to y point of node to be put in: 5>4 so will be right child again 
                      \
result:               (3,5) will be put here
 

Our K-d tree implementation can be visualized through the KDTreeVisualizer file by entering one of the txt files in the prompt when running the visualizer.
Each text file contain a different number of points to be added to the tree. 

Range Search and Nearest Neighbor Search are visualized in RangeSearch and NearestNeighbor Visualizers respectively. Red points are the points found when using the
methods in PointMap.java that simply look through all nodes in the tree and blue nodes are the ones found using the efficient pruning in KdTree that only looks
through nodes and its subtrees when those nodes could possibly be contained query rectangle or closer to a query point than the closest point found so far. 
