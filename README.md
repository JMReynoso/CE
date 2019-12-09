# Multi-robot exploration

This program implements algorithms from the "Multi-robot exploration under the constraints of wireless networking."

The purpose of this program is to explore an offline enviornment using multiple robots in a wireless network. Each robots receives a reward for
each possible movements a robot can have. For example, a robot receives -15 as a reward if the movement it wants to go to is an obstacle.
Another reward would be -10 if the robot is out of range or if the node has already been visited. 

Rendering the possible movements in a large enviornment, having more than 3 robots, and the max number
of movements a robot can have may increase the run time of the program. 

This program uses the GraphStreamAPI in order to execute visulization of the enviornment and the movments the robot makes in real time.
