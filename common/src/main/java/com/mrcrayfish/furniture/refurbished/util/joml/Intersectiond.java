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

public final class Intersectiond
{
    public static double findClosestPointsLineSegments(double a0X, double a0Y, double a0Z, double a1X, double a1Y, double a1Z, double b0X, double b0Y, double b0Z, double b1X, double b1Y, double b1Z, Vector3d resultA, Vector3d resultB)
    {
        double d1x = a1X - a0X, d1y = a1Y - a0Y, d1z = a1Z - a0Z;
        double d2x = b1X - b0X, d2y = b1Y - b0Y, d2z = b1Z - b0Z;
        double rX = a0X - b0X, rY = a0Y - b0Y, rZ = a0Z - b0Z;
        double a = d1x * d1x + d1y * d1y + d1z * d1z;
        double invA = 1.0 / a;
        double e = d2x * d2x + d2y * d2y + d2z * d2z;
        double f = d2x * rX + d2y * rY + d2z * rZ;
        double EPSILON = 1E-8;
        double s, t;
        if(a <= EPSILON && e <= EPSILON)
        {
            resultA.set(a0X, a0Y, a0Z);
            resultB.set(b0X, b0Y, b0Z);
            return resultA.dot(resultB);
        }
        if(a <= EPSILON)
        {
            s = 0.0;
            t = f / e;
            t = Math.min(Math.max(t, 0.0), 1.0);
        }
        else
        {
            double c = d1x * rX + d1y * rY + d1z * rZ;
            if(e <= EPSILON)
            {
                t = 0.0;
                s = Math.min(Math.max(-c * invA, 0.0), 1.0);
            }
            else
            {
                double b = d1x * d2x + d1y * d2y + d1z * d2z;
                double denom = a * e - b * b;
                if(denom != 0.0)
                {
                    s = Math.min(Math.max((b * f - c * e) / denom, 0.0), 1.0);
                }
                else
                {
                    s = 0.0;
                }
                t = (b * s + f) / e;
                if(t < 0.0)
                {
                    t = 0.0;
                    s = Math.min(Math.max(-c * invA, 0.0), 1.0);
                }
                else if(t > 1.0)
                {
                    t = 1.0;
                    s = Math.min(Math.max((b - c) * invA, 0.0), 1.0);
                }
            }
        }
        resultA.set(a0X + d1x * s, a0Y + d1y * s, a0Z + d1z * s);
        resultB.set(b0X + d2x * t, b0Y + d2y * t, b0Z + d2z * t);
        double dX = resultA.x - resultB.x, dY = resultA.y - resultB.y, dZ = resultA.z - resultB.z;
        return dX * dX + dY * dY + dZ * dZ;
    }
}
