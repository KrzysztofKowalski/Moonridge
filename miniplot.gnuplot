set terminal pngcairo size 2048, 1080 enhanced font "Arial, 11"
set output "performance_chart_mini.png"

set title "Performance Chart"
set xlabel "Iterations"
set ylabel "Computation Time (nanoseconds)"

set key top left
set format y "%.0f"
set format x "%.0f"


plot "performance_data_mini.txt" using 1:2 with lines title "ArrayList", \
     "performance_data_mini.txt" using 1:3 with lines title "HashMap", \
     "performance_data_mini.txt" using 1:4 with lines title "LinkedList", \
     "performance_data_mini.txt" using 1:5 with lines title "Vector"