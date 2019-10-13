package org.bugapi.bugset.base.util.jts;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.string.StringUtil;

/**
 * Jts的工具类(一套空间数据操作的核心算法。为在兼容OGC标准的空间对象模型中进行基础的几何操作提供2D空间谓词API)
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class JtsTools {

	/**
	 * 几何工厂
	 */
	private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

	/**
	 * 面字符串
	 */
	private static final String POLYGON = "POLYGON";

	/**
	 * 多面字符串
	 */
	private static final String MULTIPOLYGON = "MULTIPOLYGON";


	/**
	 * 根据传入的经度、维度字符串生成一个点对象
	 *
	 * @param lonStr,latStr 【109.013388, 32.715519】 经纬度字符串
	 * @return Point 坐标点
	 */
	public static Point createPoint(String lonStr, String latStr) {
		if (StringUtil.isEmpty(lonStr) || StringUtil.isEmpty(latStr)) {
			throw new RuntimeException("经度字符串和维度字符串不能为空");
		}
		try {
			Coordinate coord = new Coordinate(Double.parseDouble(lonStr), Double.parseDouble(latStr));
			return GEOMETRY_FACTORY.createPoint(coord);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的经度、维度字符串生成一个点对象出错", e);
		}
	}

	/**
	 * 根据传入的点字符串生成一个点对象
	 *
	 * @param pointsStr 【POINT (109.013388 32.715519)】
	 * @return Point 点对象
	 */
	public static Point createPointByWkt(String pointsStr) {
		if (StringUtil.isEmpty(pointsStr)) {
			throw new RuntimeException("经维度坐标字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (Point) reader.read(pointsStr);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的点字符串生成一个点对象出错", e);
		}
	}

	/**
	 * 根据传入的多点字符串生成一个多点对象
	 *
	 * @param multiPointStr 【MULTIPOINT(109.013388 32.715519,119.32488 31.435678)】
	 * @return MultiPoint 多点对象
	 */
	public static MultiPoint createMulPointByWkt(String multiPointStr) {
		if (StringUtil.isEmpty(multiPointStr)) {
			throw new RuntimeException("经维度坐标字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (MultiPoint) reader.read(multiPointStr);
		} catch (ParseException e) {
			throw new RuntimeException("根据传入的多点字符串生成一个多点对象出错", e);
		}
	}

	/**
	 * 根据传入的线字符串生成一个线对象
	 *
	 * @param lineStr 【LINESTRING(0 0, 2 0)】
	 * @return LineString 创建一个线对象
	 */
	public static LineString createLineByWkt(String lineStr) {
		if (StringUtil.isEmpty(lineStr)) {
			throw new RuntimeException("线字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (LineString) reader.read(lineStr);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的线字符串生成一个线对象出错", e);
		}
	}

	/**
	 * 根据传入的多线字符串生成一个多线对象
	 *
	 * @param multiLineStr 【MULTILINESTRING((0 0, 2 0),(1 1,2 2))】
	 * @return MultiLineString 创建一个多线对象
	 */
	public static MultiLineString createMultiLineByWkt(String multiLineStr) {
		if (StringUtil.isEmpty(multiLineStr)) {
			throw new RuntimeException("多线字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (MultiLineString) reader.read(multiLineStr);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的多线字符串生成一个多线对象出错", e);
		}
	}

	/**
	 * 根据传入的面对象字符串生成一个面对象
	 *
	 * @param polygonStr 【POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))】
	 * @return Polygon 面对象
	 */
	public static Polygon createPolygonByWkt(String polygonStr) {
		if (StringUtil.isEmpty(polygonStr)) {
			throw new RuntimeException("面对象字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (Polygon) reader.read(polygonStr);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的面对象字符串生成一个面对象出错", e);
		}
	}

	/**
	 * 根据传入的多面对象字符串生成一个面对象
	 *
	 * @param multiPolygonStr 【MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))】
	 * @return MultiPolygon 多面对象
	 */
	public static MultiPolygon createMultiPolygonByWkt(String multiPolygonStr) {
		if (StringUtil.isEmpty(multiPolygonStr)) {
			throw new RuntimeException("多面对象字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return (MultiPolygon) reader.read(multiPolygonStr);
		} catch (Exception e) {
			throw new RuntimeException("根据传入的多面对象字符串生成一个多面对象出错", e);
		}
	}

	/**
	 * 创建一个圆，圆心(x,y) 半径RADIUS 的面对象
	 *
	 * @param x      圆心的经度
	 * @param y      圆心的维度
	 * @param radius 圆心的半径；
	 * @return Polygon 面对象
	 */
	public static Polygon createCircle(double x, double y, final double radius) {
		// 圆上面的点个数
		final int sides = 32;
		Coordinate[] coords = new Coordinate[sides + 1];
		for (int i = 0; i < sides; i++) {
			double angle = ((double) i / (double) sides) * Math.PI * 2.0;
			double dx = Math.cos(angle) * radius;
			double dy = Math.sin(angle) * radius;
			coords[i] = new Coordinate(x + dx, y + dy);
		}
		coords[sides] = coords[0];
		LinearRing ring = GEOMETRY_FACTORY.createLinearRing(coords);
		return GEOMETRY_FACTORY.createPolygon(ring, null);
	}

	/**
	 * 根据传入的几何图形字符串返回一个几个图形
	 *
	 * @param geometryStr 几何图形的点集合字符串【POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))或者MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))】
	 * @return Geometry 几何图形
	 */
	public static Geometry createGeometryByWkt(String geometryStr) {
		if (StringUtil.isEmpty(geometryStr)) {
			throw new RuntimeException("集合图形字符串不能为空");
		}
		try {
			WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
			return reader.read(geometryStr);
		} catch (ParseException e) {
			throw new RuntimeException("根据传入的几何图形字符串返回一个几个图形出错", e);
		}
	}

	/*  以上是生成几何对象的方法  */

	/*  以下是几何对象关系判断的方法 */

	/**
	 * 获取两个多边形的交集
	 *
	 * @param geometryStr1 几何对象字符串1
	 * @param geometryStr2 几何对象字符串2
	 * @return String 交集部分几何图形的字符串
	 */
	public static String getGeometryIntersection(String geometryStr1, String geometryStr2) {
		Geometry pgeometry = createGeometryByWkt(geometryStr1);
		Geometry cgeometry = createGeometryByWkt(geometryStr2);
		if (null != pgeometry) {
			return geometryTrim(pgeometry.intersection(cgeometry));
		}
		return "";
	}

	/**
	 * 判断点对象是否在面对象里边
	 *
	 * @param point    点对象
	 * @param geometry 几何对象
	 * @return boolean
	 */
	public static boolean pointWithinGeometry(Point point, Geometry geometry) {
		if (null == point || null == geometry) {
			throw new RuntimeException("点对象和面对象不能为空");
		}
		try {
			return point.within(geometry);
		} catch (Exception e) {
			throw new RuntimeException("判断点对象是否在面对象里边出错", e);
		}
	}

	/**
	 * 判断点对象是否在面对象里边
	 *
	 * @param lonStr      经度字符串
	 * @param latStr      维度字符串
	 * @param geometryStr 面对象字符串【POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))或者MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))】
	 * @return boolean
	 */
	public static boolean pointWithinGeometry(String lonStr, String latStr, String geometryStr) {
		Point point = createPoint(lonStr, latStr);
		Geometry geometry = createGeometryByWkt(geometryStr);
		return pointWithinGeometry(point, geometry);
	}

	/**
	 * 判断两个几何对象是否重叠
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return boolean
	 */
	public static boolean geometryEqualsGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.equals(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("判断两个几何对象是否重叠出错", e);
		}
	}

	/**
	 * 判断两个几何对象没有交点(相邻)
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return boolean
	 */
	public static boolean geometryDisjointGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.disjoint(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("判断两个几何对象没有交点出错", e);
		}
	}

	/**
	 * 判断两个结合图形相交
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return boolean 【true：相交】
	 */
	public static boolean geometryIntersectsGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.intersects(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("判断两个结合图形相交出错", e);
		}
	}

	/**
	 * 返回geometry指定距离内的多边形和多多边形（包含所有的点在一个指定距离内的多边形和多多边形）
	 *
	 * @param geometry 结合对象
	 * @param distance 距离
	 * @return Geometry 新的几何对象
	 */
	public static Geometry geometryBufferGeometry(Geometry geometry, double distance) {
		if (null == geometry) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry.buffer(distance);
		} catch (Exception e) {
			throw new RuntimeException("返回geometry指定距离内的多边形和多多边形出错", e);
		}
	}

	/**
	 * 返回(geometry1)与(geometry2)中距离最近的两个点的距离
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return double 距离
	 */
	public static double geometryDistanceGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.distance(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("判断两个结合图形相交出错", e);
		}
	}

	/**
	 * 两个几何对象的交集（A∩B 交叉操作就是多边形AB中所有共同点的集合）
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return Geometry 新的几何对象
	 */
	public static Geometry geometryIntersectionGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.intersection(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("求两个几何对象的交集出错", e);
		}
	}

	/**
	 * 获取两个多边形的并集
	 *
	 * @param geometryStr1 几何点字符串1
	 * @param geometryStr2 几何点字符串2
	 * @return String 多边形字符串
	 */
	public static String geometryUnionGeometry(String geometryStr1, String geometryStr2) {
		Geometry pgeometry = createGeometryByWkt(geometryStr1);
		Geometry cgeometry = createGeometryByWkt(geometryStr2);
		Geometry ngeometry = null;

		if (null != pgeometry) {
			return geometryTrim(pgeometry.union(cgeometry));
		}
		return "";
	}

	/**
	 * 处理 Geometry 对象中的空字符串
	 *
	 * @param ngeometry 几何对象
	 * @return String 处理后的几何对象字符串
	 */
	private static String geometryTrim(Geometry ngeometry) {
		if (null != ngeometry && !ngeometry.isEmpty()) {
			String type = ngeometry.getGeometryType();
			if (POLYGON.equalsIgnoreCase(type) || MULTIPOLYGON.equalsIgnoreCase(type)) {
				return ngeometry.toString().replaceAll(", ", SymbolType.COMMA).replaceAll("POLYGON ", POLYGON).replaceAll(
						"MULTIPOLYGON ", MULTIPOLYGON);
			}
		}
		return "";
	}

	/**
	 * 两个几何对象合并（AUB AB的联合操作就是AB所有点的集合并集）
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return Geometry 新的几何对象
	 */
	public static Geometry geometryUnionGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.union(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("两个几何对象合并出错", e);
		}
	}

	/**
	 * 在geometry1几何对象中有的，但是geometry2几何对象中没有（(A-A∩B) AB形状的差异分析就是A里有B里没有的所有点的集合）
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return Geometry 新的几何对象
	 */
	public static Geometry geometryDifferenceGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.difference(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("两个几何对象合并出错", e);
		}
	}

	/**
	 * 在geometry1几何对象中有的，但是geometry2几何对象中没有（(A-A∩B) AB形状的差异分析就是A里有B里没有的所有点的集合）
	 *
	 * @param geometry1 几何对象1
	 * @param geometry2 几何对象2
	 * @return Geometry 新的几何对象
	 */
	public static Geometry geometrySymDifferenceGeometry(Geometry geometry1, Geometry geometry2) {
		if (null == geometry1 || null == geometry2) {
			throw new RuntimeException("几何对象不能为空");
		}
		try {
			return geometry1.symDifference(geometry2);
		} catch (Exception e) {
			throw new RuntimeException("两个几何对象合并出错", e);
		}
	}
}