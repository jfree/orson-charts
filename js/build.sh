#!/bin/bash

java -jar ~/jars/compiler.jar --warning_level VERBOSE --compilation_level WHITESPACE_ONLY \
--js src/Utils.js \
--js src/KeyedValuesDataset.js \
--js src/KeyedValues3DDataset.js \
--js src/XYZDataset.js \
--js src/KeyedValueLabels.js \
--js src/KeyedValue3DLabels.js \
--js src/XYZLabels.js \
--js_output_file orsoncharts.js