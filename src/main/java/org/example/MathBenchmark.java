/*
 *
 * This file is part of arche-quickstart.
 *  Copyright (c) 2023.
 * arche-quickstart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [Project Name] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with arche-quickstart. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.example;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class MathBenchmark {


    public MathBenchmark() {
    }

    public long benchmarkAddition(int a, int b) {

        // Code snippet for addition
//        int result = 2 + 3;
        return (long) a + b;
//        return result;
    }

    public long benchmarkMultiplication(int a, int b) {
//        int result = 2 * 3;
        return (long) a + b;
//        return result;
    }

    void scalarComputation(float[] a, float[] b, float[] c) {
        for (int i = 0; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
    }

    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    void vectorComputation(float[] a, float[] b, float[] c) {
        int i = 0;
        int upperBound = SPECIES.loopBound(a.length);
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc;
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            var vc = va.mul(va)
                    .add(vb.mul(vb))
                    .neg();
            vc.intoArray(c, i);
        }
        for (; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
    }
}
