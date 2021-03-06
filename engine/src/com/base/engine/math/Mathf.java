package com.base.engine.math;

public class Mathf {
	private Mathf(){}
	
	@FunctionalInterface
	public interface Function {
		float f(float x);
	}
	
	public static final float ROOT_DIFFERENCE = 1e-8f;
	public static final float PI = (float) Math.PI;
	
	//--------------------------------------------------------------------
	//--------------------------General-----------------------------------
	//--------------------------------------------------------------------
	
	public static float avg(float...ds){
		float res = 0;
		for (int i = 0; i < ds.length; i++) 
			res += ds[i];
		return res / ds.length;
	}
	public static boolean validFloat(float f){
		return !Float.isNaN(f) && Float.isFinite(f);
	}
	/**
	 * Limits a given degree to a range of 0 to 360. The given angle must be in degrees.
	 * 
	 * If the angle is between 0 and 360 it will be returned. Otherwise, if its absolute value is bigger 
	 * than 360, the angle will be reduced to the corresponding value between 0 and 360 according to the unit
	 * circle. If the angle is negative, its value will be changed to a corresponding positive angle according
	 * to the unit circle.
	 * 
	 * @param value An angle in degrees to limit.
	 * @return The value of the angle after being limited.
	 */
	public static float limitAngle(float value){
		value %= 360;
		if(value < 0)
			value += 360;
		return value;
	}
	/**
	 * Makes sure that a given value is within a given limit. If the value is outside that limit, its value
	 * will be changed to meet the limit accordingly:
	 * value bigger than max : value = max
	 * value smaller than min : value = min
	 * 
	 * @param value The value to limit
	 * @param min The minimum limit
	 * @param max The maximum limit
	 * @return The new value after making sure it is within the given limit.
	 */
	public static float limit(float value, float min, float max){
		if(value > max) value = max;
		else if(value < min) value = min;
		return value;
	}
	public static boolean limited(float value, float min, float max){
		return value >= min && value <= max;
	}
	/**
	 * Rounds a decimal number to 2 numbers after the decimal point.
	 * 
	 * @param x A decimal value to round
	 * @return The rounded value
	 */
	public static float roundDecimal(float x){
		return roundDecimal(x, 2);
	}
	/**
	 * Rounds a decimal number to a give amount of numbers after the decimal point.
	 * @param x A decimal value to round
	 * @param decimalNums Amount of numbers after the decimal point
	 * @return The rounded value
	 */
	public static float roundDecimal(float x, int decimalNums){
		float m = pow(10, decimalNums);
		return Math.round(x * m) / m;
	}
	public static float roundToMultiplier(float val, float multiplier){
		return multiplier * Math.round(val / multiplier);
	}
	public static float roundToMultiplier(float val, float multiplier, boolean up){
		float rounded = roundToMultiplier(val, multiplier);
		if(rounded < val)
			rounded += up? multiplier : -multiplier;
		return rounded;
	}
	/**
	 * Gets the result of Pythagorases theorem for a given set of numbers.
	 * 
	 * The Pythagorean theorem states that:
	 * In any right triangle, the area of the square whose side is the hypotenuse 
	 * (the side opposite the right angle) is equal to the sum of the areas of the squares whose 
	 * sides are the two legs (the two sides that meet at a right angle).
	 * 
	 * This method implements it in a 3d space.
	 * 
	 * @param a a
	 * @param b a
	 * @param c a
	 * @return The result for pythagorasTheorem for given values
	 */
	public static float pythagorasTheorem(float a, float b, float c){
		return sqrt((a * a) + (b * b) + (c * c));
	}
	/**
	 * Gets the result of Pythagorases theorem for a given set of numbers.
	 * 
	 * The Pythagorean theorem states that:
	 * In any right triangle, the area of the square whose side is the hypotenuse 
	 * (the side opposite the right angle) is equal to the sum of the areas of the squares whose 
	 * sides are the two legs (the two sides that meet at a right angle).
	 * 
	 * @param a a
	 * @param b a
	 * @return The area of the square whose side is the hypotenuse
	 */
	public static float pythagorasTheorem(float a, float b){
		return pythagorasTheorem(a, b, 0);
	}
	public static float reversePythagorasTheorem(float a, float c){
		return sqrt((c * c) - (a * a));
	}
	public static int multiply(int a, int b){
		return (a << (b / 2)) + ((b % 2 != 0)? a : 0);
	}
	/**
	 * Calculates the nth root of a given number.
	 * 
	 * @param result The result of the base in the power of exponent.
	 * @param exponent The root exponent
	 * @return The base who when multiplied exponent times returns the given result
	 * @throws IllegalArgumentException if result is negative
	 */
	public static float root(float result, int exponent){
		if(result < 0)
			throw new IllegalArgumentException("Cannot calculate negative root! Use complexRoot instead");
        if(result == 0) 
            return 0;
        
        float x1 = result;
        float x2 = result / exponent;  
        while (Math.abs(x1 - x2) > ROOT_DIFFERENCE){
            x1 = x2;
            x2 = ((exponent - 1.0f) * x2 + result / pow(x2, exponent - 1.0f)) / exponent;
        }
        return x2;
	}
	
	public static float sineLaw(float a, float ratio){
		return toDegrees(asin(a / ratio));
	}
	public static float reverseSineLaw(float alpha, float ratio){
		return ratio * toDegrees(sin(alpha));
	}
	public static float cosineLaw(float a, float b, float angle){
		return ((a * a) + (b * b) - 2 * a * b * toDegrees(cos(angle)));
	}
	public static float reverseCosineLaw(float a, float b, float c){
		return acos(-(c * c - a * a - b * b) / (2 * a * b));
	}
	public static float discriminant(float a, float b, float c){
		return (b * b) - 4 * a * c;
	}
	public static float[] quadraticFormula(float a, float b, float c){
		float root = sqrt(discriminant(a, b, c));
		return new float[]{(-b + root)/(2 * a), (-b - root)/(2 * a)};
	}
	
	public static float factorial(float n){
		float result = 1;
		for (int i = 2; i <= n; i++) 
			result *= n;
		return result;
	}
	
	public static float cos(float rad){
		return (float) Math.cos(rad);
	}
	public static float sin(float rad){
		return (float) Math.sin(rad);
	}
	public static float tan(float rad){
		return (float) Math.tan(rad);
	}
	public static float acos(float rad){
		return (float) Math.acos(rad);
	}
	public static float asin(float rad){
		return (float) Math.asin(rad);
	}
	public static float atan(float rad){
		return (float) Math.atan(rad);
	}
	public static float atan2(float y, float x){
		return (float) Math.atan2(y, x);
	}
	
	public static float sqrt(float sq){
		return (float) Math.sqrt(sq);
	}
	public static float pow(float base, float exp){
		return (float) Math.pow(base, exp);
	}
	
	public static float toRadians(float deg){
		return deg / 180.0f * PI;
	}
	public static float toDegrees(float rad){
		return rad * 180.0f / PI;
	}
	
	//--------------------------------------------------------------------
	//--------------------------Matrices----------------------------------
	//--------------------------------------------------------------------
	
	public static float[][] multiplyMat(float[][] mat1, float[][] mat2){
		if(mat1[0].length != mat2.length) 
			throw new IllegalArgumentException("Cannot multiply matricies");
		float[][] result = new float[mat2.length][mat2[0].length];
		for(int i = 0; i < result[0].length; i++){
			for(int j = 0; j < result.length; j++){
				float value = 0;
				for(int k = 0; k < result.length; k++)
					value += mat1[j][k] * mat2[k][i];
				result[j][i] = value;
			}
		}
		return result;
	}
	public static float[][] multiplyMat(float[][]...mats){
		if(mats == null || mats.length < 2)
			throw new IllegalArgumentException("Insufficent matrices to multiply");
		
		float[][] mat = mats[0];
		for(int i = 1; i < mats.length; i++)
			mat = multiplyMat(mat, mats[i]);
		return mat;
	}
	public static float[][] rotationMatrix3d(float x, float y, float z){
		return multiplyMat(rotationMatrix3dX(x), rotationMatrix3dY(y), rotationMatrix3dZ(z));
	}
	public static float[][] rotationMatrix3dX(float angle){
		angle = toRadians(angle);
		return new float[][]{
			{1, 0, 0, 0},
			{0, cos(angle), (float) -sin(angle), 0},
			{0, sin(angle), cos(angle), 0},
			{0,0,0,1}
		};
	}
	public static float[][] rotationMatrix3dY(float angle){
		angle = toRadians(angle);
		return new float[][]{
			{cos(angle), 0, sin(angle),0},
			{0, 1, 0,0},
			{-sin(angle), 0, cos(angle),0},
			{0,0,0,1}
		};
	}
	public static float[][] rotationMatrix3dZ(float angle){
		angle = toRadians(angle);
		return new float[][]{
			{cos(angle), (float) -sin(angle), 0,0},
			{sin(angle), cos(angle), 0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
	}
	public static float[][] translationMatrix3d(float x, float y, float z){
		return new float[][]{
			{1,0,0,x},
			{0,1,0,y},
			{0,0,1,z},
			{0,0,0,1}
		};
	}
	public static float[][] identityMatrix3d(){
		return new float[][]{
			{1,0,0,0},
			{0,1,0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
	}
	public static float[][] perspectiveMatrix3d(float fov, float aspectRatio, float zNear, float zFar){
		fov = toRadians(fov);
		float tanHalfFOV = (float)tan(fov / 2);
		float zRange = zNear - zFar;
		
		return new float[][]{
			{1.0f / (tanHalfFOV * aspectRatio),0,0,0},
			{0,1.0f / tanHalfFOV,0,0},
			{0,0,(-zNear -zFar)/zRange,2 * zFar * zNear / zRange},
			{0,0,1.0f,0}
		};
	}
	public static float[][] scalingMatrix3d(float x, float y, float z){
		return new float[][]{
			{x,0,0,0},
			{0,y,0,0},
			{0,0,z,0},
			{0,0,0,1}
		};
	}
	public static void reverseMatrixValues(float[][] mat){
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat[0].length; j++)
				mat[i][j] *= -1;
		}
	}
	public static float[][] reversedMatrix(float[][] mat){
		float[][] mat2 = new float[mat.length][mat[0].length];
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat[0].length; j++)
				mat2[i][j] = -1 * mat[i][j];
		}
		return mat2;
	}
	public static float[][] rotatePoint(float[][] pointAsMat, float[][] rotationMat, float[][] translationMat){
		float[][] res = multiplyMat(rotationMat, translationMat, pointAsMat);
		return multiplyMat(res, reversedMatrix(translationMat), pointAsMat);
	}
	
	//--------------------------------------------------------------------
	//--------------------------Derivatives---------------------------------
	//--------------------------------------------------------------------
	
	public static float derive(Function func, float x){
		return centralDifference(func, x);
	}
	public static float derive2(Function func, float x){
		return centralDifference2(func, x);
	}
	public static float forwardDifference(Function func, float x){
		return forwardDifference(func, x, 1e-8f);
	}
	public static float forwardDifference(Function func, float x, float changeConstant){
		return (func.f(x + changeConstant) - func.f(x)) / changeConstant;
	}
	public static float backwardDifference(Function func, float x){
		return backwardDifference(func, x, 1e-8f);
	}
	public static float backwardDifference(Function func, float x, float changeConstant){
		return (func.f(x) - func.f(x - changeConstant)) / changeConstant;
	}
	public static float centralDifference(Function func, float x){
		return centralDifference(func, x, 1e-8f);
	}
	public static float centralDifference(Function func, float x, float changeConstant){
		return (func.f(x + changeConstant) - func.f(x - changeConstant)) / (2 * changeConstant);
	}
	public static float centralDifference2(Function func, float x){
		return centralDifference2(func, x, 1e-8f);
	}
	public static float centralDifference2(Function func, float x, float changeConstant){
		return (func.f(x + changeConstant) - 2 * func.f(x) + func.f(x - changeConstant)) / (changeConstant * changeConstant);
	}
	
	//--------------------------------------------------------------------
	//--------------------------Integrals---------------------------------
	//--------------------------------------------------------------------
	
	public static float integrate(Function func, float min, float max){
		return simpsonsRule(func, min, max);
	}
	public static float trapezoidalRule(Function func, float min, float max){
		final int DEFAULT_INTEGRAL_TREPAZOIDS = 100;
		return trapezoidalRule(func, min, max, DEFAULT_INTEGRAL_TREPAZOIDS);
	}
	public static float trapezoidalRule(Function func, float min, float max, int trapezoids){
		float h = (max - min) / trapezoids;
		float s = 0.5f * (func.f(min) + func.f(max));
		for(int i = 1; i < trapezoids; i++)
			s += func.f(min + i * h); 
		return (s * h);
	}
	public static float simpsonsRule(Function func, float min, float max){
		final int DEFAULT_INTEGRAL_SLICES = 10;
		return simpsonsRule(func, min, max, DEFAULT_INTEGRAL_SLICES);
	}
	public static float simpsonsRule(Function func, float min, float max, int slices){
		float h = (max - min) / slices;
		float s = func.f(min) + func.f(max), s1 = 0, s2 = 0;
		for(int i = 1; i <= slices / 2; i++){
			s1 += func.f(min + (2 * i - 1) * h);
			if(i < slices / 2) s2 += func.f(min + 2 * i * h);
		}
		return (1 / 3.0f) * h * (s + 4 * s1 + 2 * s2);
	}
	
	//--------------------------------------------------------------------
	//--------------------------Vectors-----------------------------------
	//--------------------------------------------------------------------
	
	public static float VecInclination(float z, float magnitude){
		float angle = toDegrees(acos(z / magnitude)); 
		return (z < 0)? -angle : angle;
	}
	public static float vecAzimuth(float y, float x){
		return toDegrees(atan2(y, x));
	}
	public static float vecMagnitude(float x, float y){
		return pythagorasTheorem(x, y);
	}
	public static float vecMagnitude(float x, float y, float z){
		return pythagorasTheorem(x, y, z);
	}
	public static float vecX(float magnitude, float azimuth, float inclination){
		return (float) (magnitude * sin(toRadians(inclination)) * cos(toRadians(azimuth)));
	}
	public static float vecX(float magnitude, float azimuth){
		return (float) (magnitude * cos(toRadians(azimuth)));
	}
	public static float vecY(float magnitude, float azimuth, float inclination){
		return (float) (magnitude * sin(toRadians(inclination)) * sin(toRadians(azimuth)));
	}
	public static float vecY(float magnitude, float azimuth){
		return (float) (magnitude * sin(toRadians(azimuth)));
	}
	public static float vecZ(float magnitude, float inclination){
		return (float) (magnitude * cos(toRadians(inclination)));
	}
}
/*
	public static float derive(Functionf func, float x){
		return centralDifference(func, x);
	}
	public static float derive2(Functionf func, float x){
		return centralDifference2(func, x);
	}
	public static float forwardDifference(Functionf func, float x){
		return forwardDifference(func, x, 1e-8f);
	}
	public static float forwardDifference(Functionf func, float x, float changeConstant){
		return (func.f(x + changeConstant) - func.f(x)) / changeConstant;
	}
	public static float backwardDifference(Functionf func, float x){
		return backwardDifference(func, x, 1e-8f);
	}
	public static float backwardDifference(Functionf func, float x, float changeConstant){
		return (func.f(x) - func.f(x - changeConstant)) / changeConstant;
	}
	public static float centralDifference(Functionf func, float x){
		return centralDifference(func, x, 1e-8f);
	}
	public static float centralDifference(Functionf func, float x, float changeConstant){
		return (func.f(x + changeConstant) - func.f(x - changeConstant)) / (2 * changeConstant);
	}
	public static float centralDifference2(Functionf func, float x){
		return centralDifference2(func, x, 1e-8f);
	}
	public static float centralDifference2(Functionf func, float x, float changeConstant){
		return (func.f(x + changeConstant) - 2 * func.f(x) + func.f(x - changeConstant)) / (changeConstant * changeConstant);
	}

*/
