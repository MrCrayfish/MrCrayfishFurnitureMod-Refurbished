/*
 * The MIT License
 *
 * Copyright (c) 2015-2024 Kai Burjack
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mrcrayfish.furniture.refurbished.util.joml;

public class Intersectionf
{
    public static boolean intersectLineLine(float ps1x, float ps1y, float pe1x, float pe1y, float ps2x, float ps2y, float pe2x, float pe2y, Vector2f p)
    {
        float d1x = ps1x - pe1x;
        float d1y = pe1y - ps1y;
        float d1ps1 = d1y * ps1x + d1x * ps1y;
        float d2x = ps2x - pe2x;
        float d2y = pe2y - ps2y;
        float d2ps2 = d2y * ps2x + d2x * ps2y;
        float det = d1y * d2x - d2y * d1x;
        if(det == 0.0f) return false;
        p.x = (d2x * d1ps1 - d1x * d2ps2) / det;
        p.y = (d1y * d2ps2 - d2y * d1ps1) / det;
        return true;
    }
}
