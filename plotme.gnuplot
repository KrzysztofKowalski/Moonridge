set terminal pngcairo size 2048, 1080 enhanced font "Arial, 11"
set output "performance_chart.png"

set title "Performance Chart"
set xlabel "Iterations"
set ylabel "Computation Time (nanoseconds)"

set key top left
set format y "%'.0f"   # Use thousands separators in y-axis labels
set format x "%.0f"

plot "performance_data.txt" using 1:2 with lines title "ArrayDeque", \
     "performance_data.txt" using 1:3 with lines title "ArrayList", \
     "performance_data.txt" using 1:4 with lines title "HashMap", \
     "performance_data.txt" using 1:5 with lines title "HashSet", \
     "performance_data.txt" using 1:6 with lines title "LinkedHashSet", \
     "performance_data.txt" using 1:7 with lines title "LinkedList", \
     "performance_data.txt" using 1:8 with lines title "PriorityQueue", \
     "performance_data.txt" using 1:9 with lines title "Stack", \
     "performance_data.txt" using 1:10 with lines title "TreeMap", \
     "performance_data.txt" using 1:11 with lines title "TreeSet", \
     "performance_data.txt" using 1:12 with lines title "Vector"