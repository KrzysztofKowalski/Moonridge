#!/bin/bash

gnuplot plotme.gnuplot
head -n 18 performance_data.txt >performance_data_mini.txt
gnuplot miniplot.gnuplot
