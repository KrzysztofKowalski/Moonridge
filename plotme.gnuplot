set terminal pngcairo size 8192, 4320 enhanced font "Arial, 69"
set output "performance_chart.png"

set title "Performance Chart"
set xlabel "Iterations"
set ylabel "Computation Time (nanoseconds)"

set key top left
set format y "%'.0f"   # Use thousands separators in y-axis labels
set format x "%.0f"

# Define a variable for linewidth
lw = 22

# Define unique colors for each data structure
arraydeque_color = "#1f77b4"
arraylist_color = "#ff7f0e"
hashmap_color = "#2ca02c"
hashset_color = "#d62728"
linkedhashset_color = "#9467bd"
linkedlist_color = "#8c564b"
priorityqueue_color = "#e377c2"
stack_color = "#7f7f7f"
treemap_color = "#bcbd22"
treeset_color = "#17becf"
vector_color = "#7f7f7f"

plot "performance_data_custom.txt" using 1:2 with lines linewidth lw linecolor rgb arraydeque_color title "ArrayDeque", \
     "performance_data_custom.txt" using 1:3 with lines linewidth lw linecolor rgb arraylist_color title "ArrayList", \
     "performance_data_custom.txt" using 1:4 with lines linewidth lw linecolor rgb hashmap_color title "HashMap", \
     "performance_data_custom.txt" using 1:5 with lines linewidth lw linecolor rgb hashset_color title "HashSet", \
     "performance_data_custom.txt" using 1:6 with lines linewidth lw linecolor rgb linkedhashset_color title "LinkedHashSet", \
     "performance_data_custom.txt" using 1:7 with lines linewidth lw linecolor rgb linkedlist_color title "LinkedList", \
     "performance_data_custom.txt" using 1:8 with lines linewidth lw linecolor rgb priorityqueue_color title "PriorityQueue", \
     "performance_data_custom.txt" using 1:9 with lines linewidth lw linecolor rgb stack_color title "Stack", \
     "performance_data_custom.txt" using 1:10 with lines linewidth lw linecolor rgb treemap_color title "TreeMap", \
     "performance_data_custom.txt" using 1:11 with lines linewidth lw linecolor rgb treeset_color title "TreeSet", \
     "performance_data_custom.txt" using 1:12 with lines linewidth lw linecolor rgb vector_color title "Vector"
