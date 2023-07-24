#!/bin/bash

cat performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_entire.png

head -n 33 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_head.png

head -n 24 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_low.png

head -n 20 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_ultralow.png


head -n 11 performance_data.txt >performance_data_custom.txt
gnuplot plotme.gnuplot
mv performance_chart.png performance_chart_tip.png

head -n 19 performance_data_without_trees.txt \
  >performance_data_custom.txt
gnuplot notrees.gnuplot
