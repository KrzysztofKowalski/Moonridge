set terminal pngcairo size 8192, 4320 enhanced font "Arial, 69"
set output "performance_chart.png"

set title "Performance Chart"
set xlabel "Iterations"
set ylabel "Computation Time (nanoseconds)"

set key top left
set format y "%'.0f"   # Use thousands separators in y-axis labels
set format x "%.0f"

# Define a variable for linewidth
lw = 20

plot "performance_data_custom.txt" using 1:2 with lines linewidth lw title "ArrayDeque", \
     "performance_data_custom.txt" using 1:3 with lines linewidth lw title "ArrayList", \
     "performance_data_custom.txt" using 1:4 with lines linewidth lw title "HashMap", \
     "performance_data_custom.txt" using 1:5 with lines linewidth lw title "HashSet", \
     "performance_data_custom.txt" using 1:6 with lines linewidth lw title "LinkedHashSet", \
     "performance_data_custom.txt" using 1:7 with lines linewidth lw title "LinkedList", \
     "performance_data_custom.txt" using 1:8 with lines linewidth lw title "PriorityQueue", \
     "performance_data_custom.txt" using 1:9 with lines linewidth lw title "Stack", \
     "performance_data_custom.txt" using 1:10 with lines linewidth lw title "TreeMap", \
     "performance_data_custom.txt" using 1:11 with lines linewidth lw title "TreeSet", \
     "performance_data_custom.txt" using 1:12 with lines linewidth lw title "Vector"
