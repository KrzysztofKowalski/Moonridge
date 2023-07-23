set terminal pngcairo size 8192, 4320 enhanced font "Arial, 69"
set output "performance_chart_notrees.png"

set title "Performance Chart"
set xlabel "Iterations"
set ylabel "Computation Time (nanoseconds)"

set key top left
set format y "%'.0f"   # Use thousands separators in y-axis labels
set format x "%.0f"

# Define a variable for linewidth
lw = 15

# Define colors and line styles
arraydeque_color = "#0f77b4"
arraylist_color = "#ff7f0e"
hashmap_color = "#2ca02c"
hashset_color = "#d62728"
linkedhashset_color = "#9467bd"
linkedlist_color = "#8c564b"
priorityqueue_color = "#e377c2"
stack_color = "#7f7f7f"
vector_color = "#f2e8b2"

set style line 1 lc rgb arraydeque_color lt 1 lw lw
set style line 2 lc rgb arraylist_color lt 2 lw lw
set style line 3 lc rgb hashmap_color lt 3 lw lw
set style line 4 lc rgb hashset_color lt 4 lw lw
set style line 5 lc rgb linkedhashset_color lt 5 lw lw
set style line 6 lc rgb linkedlist_color lt 6 lw lw
set style line 7 lc rgb priorityqueue_color lt 7 lw lw
set style line 8 lc rgb stack_color lt 8 lw lw
set style line 9 lc rgb vector_color lt 9 lw lw

plot "performance_data_custom.txt" using 1:2 with lines linestyle 1 title "ArrayDeque", \
     "performance_data_custom.txt" using 1:3 with lines linestyle 2 title "ArrayList", \
     "performance_data_custom.txt" using 1:4 with lines linestyle 3 title "HashMap", \
     "performance_data_custom.txt" using 1:5 with lines linestyle 4 title "HashSet", \
     "performance_data_custom.txt" using 1:6 with lines linestyle 5 title "LinkedHashSet", \
     "performance_data_custom.txt" using 1:7 with lines linestyle 6 title "LinkedList", \
     "performance_data_custom.txt" using 1:8 with lines linestyle 7 title "PriorityQueue", \
     "performance_data_custom.txt" using 1:9 with lines linestyle 8 title "Stack", \
     "performance_data_custom.txt" using 1:10 with lines linestyle 9 title "Vector"
