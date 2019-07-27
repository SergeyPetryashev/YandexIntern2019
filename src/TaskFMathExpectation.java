import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class TaskFMathExpectation {
/* square vertex [0 0; 0 1; 1 1; 1 0]
 * There are N chips in this square, the i-th chip has coordinates (xi, yi). 
 * The chips have negligible small sizes; we will consider them as material points. 
 * A random point is selected with a uniform distribution in this square. 
 * Then the number of chips is counted, the distance from which to the selected point does not exceed R, 
 * and the corresponding number of points is obtained. 
 * Find the mathematical expectation of the number of points that can be gained in the game described
 */
	public static void main(String[] args)  throws NumberFormatException, IOException {
		Locale.setDefault(Locale.US);
		File file = new File("inputF.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String [] data = br.readLine().split(" ");
    	int num = Integer.parseInt(data[0]);
    	double radius = Double.parseDouble(data[1]);
 //   	radius = Double.parseDouble(args[0]);
    	
    	double sumExpectation=0.0;
    	if(radius>=Math.sqrt(2)) {
    		sumExpectation=1.0*num;
		}else {
	    	for(int i=0; i<num;i++) {
	    		String [] point = br.readLine().split(" ");
	    		sumExpectation+=expectationPoint(radius, Double.parseDouble(point[0]), Double.parseDouble(point[1]));
	    	} 
		}
    	
    	System.out.printf("%.10f\n", sumExpectation);
 //   	System.out.println(Math.cos(Math.PI));
    	br.close();
	}
	
	private static double expectationPoint(double radius, double pointX, double pointY) {
		double expectValue=0.0;
		// радиус меньше 0.5
		if(pointX-radius>=0.0 && pointX+radius<=1.0 &&	// окружность лежит полностью внутри квадрата
				pointY-radius>=0.0 && pointY+radius<=1.0) {
			expectValue=areaCircle(radius);
		}else if(pointX-radius>=0.0 && pointX+radius<=1.0) {
			double dy;
			dy= pointY>0.5 ? 1-pointY : pointY;
			double angle = 2*angleSection(radius, dy);
			expectValue = areaCircle(radius)-areaSegmentCircle(radius, angle);
		}else if(pointY-radius>=0.0 && pointY+radius<=1.0) {
			double dx;
			dx= pointX>0.5 ? 1-pointX : pointX;
			double angle = 2*angleSection(radius, dx);
			expectValue = areaCircle(radius)-areaSegmentCircle(radius, angle);
		}else if(radius<=0.5){ // радиус меньше 0.5 и касание двух граней
			double dx, dy;
			dx= pointX>0.5 ? 1-pointX : pointX;
			dy= pointY>0.5 ? 1-pointY : pointY;
			expectValue=areaTwoSide(radius, dx, dy);		
		}else if(radius>0.5) {// радиус больше 0.5
			double dxPlus, dxMinus, dyPlus, dyMinus;
			dxPlus=1-pointX;
			dxMinus=pointX;
			dyPlus=1-pointY;
			dyMinus=pointY;
			if(Math.max(dxPlus, dxMinus)>=radius && Math.max(dyPlus, dyMinus)>=radius) {// варианты с двумя пересекающими сторонами
				expectValue=areaTwoSide(radius, Math.min(dxPlus, dxMinus), Math.min(dyPlus, dyMinus));
			}else if(Math.max(dxPlus, dxMinus)>=radius) {// варианты с тремя пересекающими сторонами
				expectValue=areaThreeSide(radius, Math.min(dxPlus, dxMinus), Math.min(dyPlus, dyMinus), Math.max(dyPlus, dyMinus));
			}else if(Math.max(dyPlus, dyMinus)>=radius) {
				expectValue=areaThreeSide(radius, Math.min(dxPlus, dxMinus), Math.min(dyPlus, dyMinus), Math.max(dxPlus, dxMinus));
			}else {			/// варианты с четырьмя пересекающимися сторона
				double angleXPL = angleSection(radius, dxPlus);
				double angleXMin = angleSection(radius, dxMinus);
				double angleYPL = angleSection(radius, dyPlus);
				double angleYMin = angleSection(radius, dyMinus);
				if(dxMinus*dxMinus+dyMinus*dyMinus>radius*radius &&
						dxMinus*dxMinus+dyPlus*dyPlus>radius*radius &&
						dxPlus*dxPlus+dyMinus*dyMinus>radius*radius &&
						dxPlus*dxPlus+dyPlus*dyPlus>radius*radius) {	// не описывает  ни один угол
					expectValue=areaCircle(radius)-areaSegmentCircle(radius, 2*angleXPL)-
							areaSegmentCircle(radius, 2*angleYPL)-
							areaSegmentCircle(radius, 2*angleXMin)-
							areaSegmentCircle(radius, 2*angleYMin);
				}else if(dxMinus*dxMinus+dyMinus*dyMinus<=radius*radius &&
						dxMinus*dxMinus+dyPlus*dyPlus<=radius*radius &&
						dxPlus*dxPlus+dyMinus*dyMinus<=radius*radius &&
						dxPlus*dxPlus+dyPlus*dyPlus<=radius*radius) {// описывает  все углы
					expectValue=1.0;
				}else if(Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)>radius*radius &&
						Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.min(dyPlus, dyMinus),2)<=radius*radius &&
						Math.pow(Math.min(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)<=radius*radius){ 
						// описывает три угла  площадь квадрата минус не включенный угол
					double angleX = angleSection(radius, Math.max(dxPlus, dxMinus));
					double angleY = angleSection(radius, Math.max(dyPlus, dyMinus));
					double a = Math.max(dxPlus, dxMinus)-radius*Math.sin(angleY);
					double b = Math.max(dyPlus, dyMinus)-radius*Math.sin(angleX);
					expectValue=1.0 - (a*b)/2+areaSegmentCircle(radius, Math.PI/2-angleX-angleY);
				}else if(Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)>radius*radius &&
						Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.min(dyPlus, dyMinus),2)>radius*radius &&
						Math.pow(Math.min(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)<=radius*radius ) {
					// описывает  2 угла
					double angleX = angleSection(radius, Math.max(dxPlus, dxMinus));
					double yp = Math.max(dyPlus, dyMinus)-radius*Math.sin(angleX);
					double ym = Math.min(dyPlus, dyMinus)-radius*Math.sin(angleX);
					
					double xp = Math.max(dxPlus, dxMinus)-radius*Math.sin(Math.min(angleYPL, angleYMin));
					double xm = Math.max(dxPlus, dxMinus)-radius*Math.sin(Math.max(angleYPL, angleYMin));
					
					expectValue=1.0 - areaTriangle(xm, ym) - areaTriangle(xp, yp) + 
							areaSegmentCircle(radius, Math.PI/2-angleX-Math.min(angleYPL, angleYMin))+
							areaSegmentCircle(radius, Math.PI/2-angleX-Math.max(angleYPL, angleYMin));
				
				} else if(Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)>radius*radius &&
						Math.pow(Math.min(dxPlus, dxMinus),2)+Math.pow(Math.max(dyPlus, dyMinus),2)>radius*radius &&
						Math.pow(Math.max(dxPlus, dxMinus),2)+Math.pow(Math.min(dyPlus, dyMinus),2)<radius*radius) {
					// описывает  2 угла
					double angleY = angleSection(radius, Math.max(dyPlus, dyMinus));
					double xp = Math.max(dxPlus, dxMinus)-radius*Math.sin(angleY);
					double xm = Math.min(dxPlus, dxMinus)-radius*Math.sin(angleY);
					
					double yp = Math.max(dyPlus, dyMinus)-radius*Math.sin(Math.min(angleXPL, angleXMin));
					double ym = Math.max(dyPlus, dyMinus)-radius*Math.sin(Math.max(angleXPL, angleXMin));
					
					expectValue=1.0 - areaTriangle(xm ,ym) - areaTriangle(xp, yp) + 
							areaSegmentCircle(radius, Math.PI/2-angleY-Math.min(angleXPL, angleXMin))+
							areaSegmentCircle(radius, Math.PI/2-angleY-Math.max(angleXPL, angleXMin));
				
				}else { // описывает один угол
					double angleX = angleSection(radius, Math.max(dxPlus, dxMinus));
					double angleY = angleSection(radius, Math.max(dyPlus, dyMinus));
					expectValue = areaTwoSide(radius, Math.min(dxPlus, dxMinus), Math.min(dyPlus, dyMinus))-
							areaSegmentCircle(radius, 2*angleX)-areaSegmentCircle(radius, 2*angleY);
				}
			}
			
		}
	//	System.out.println(expectValue);
		return expectValue;
	}
	
	private static double areaThreeSide(double radius, double dx, double dy, double dxOrDy) {
		double area;
		double angleXY = angleSection(radius, dxOrDy);
		if(Math.pow(Math.min(dx, dy),2)+dxOrDy*dxOrDy>=radius*radius) {//окружность не описана вокруг второго угла
			area = areaTwoSide(radius, dx, dy)-areaSegmentCircle(radius, 2*angleXY);
		}else { // окружность описана вокруг обоих углов
			// площадь сегмента + площади трапеции
			double angle = angleSection(radius, Math.max(dx, dy));
			double a=radius*Math.sin(angle)+Math.min(dx, dy);
			double b=radius*Math.sin(angleXY)+Math.min(dx, dy);
			area = areaSegmentCircle(radius, Math.PI-angleXY-angle)+(a+b)/2;
		}
		return area;
	}
	
	private static double areaTwoSide(double radius, double dx, double dy) {
		double area;
		double angleX = angleSection(radius, dx);
		double angleY = angleSection(radius, dy);
		if(dx*dx+dy*dy>=radius*radius) {//окружность не описана вокруг угла
			area = areaCircle(radius)-areaSegmentCircle(radius, 2*angleX)-areaSegmentCircle(radius, 2*angleY);
		}else { // окружность описана вокруг угла
			// площадь сектора + площади треугольников + площадь прямоугольника
			area = areaSectorCircle(radius, 2*Math.PI-0.5*Math.PI-angleX-angleY)+
					areaTriangle(radius*Math.sin(angleX), dx)+areaTriangle(radius*Math.sin(angleY), dy)+dx*dy;
		}
		return area;
	}
	
	private static double angleSection(double radius, double delta) {
		return Math.acos(delta/radius);
	}
		
	private static double areaCircle(double radius) {
		return Math.PI*radius*radius;
	}
	// angle в  радианах
	private static double areaSectorCircle(double radius, double angle) {
		return radius*radius*angle/2;
	}
	private static double areaSegmentCircle(double radius, double angle) {
		return radius*radius*(angle-Math.sin(angle))/2;
	}
		
	// по двум катетам
	private static double areaTriangle(double a, double b) {
		return a*b/2;
	}

}
