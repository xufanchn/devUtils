import hmap.geoAnalysis.crs.transform.CrsTransformImpl;
import org.locationtech.jts.geom.Coordinate;

/**
 * @author xufan9
 * @date 2021/7/15 16:19
 * @implSpec 坐标转换工具类
 * @implNote Coordinate Util
 */
public class CoordinateUtil {

    private static final double[] ARRAY1 = new double[]{75.0D, 60.0D, 45.0D, 30.0D, 15.0D, 0.0D};
    private static final double[] ARRAY3 = new double[]{1.289059486E7D, 8362377.87D, 5591021.0D, 3481989.83D, 1678043.12D, 0.0D};
    private static final double[][] ARRAY2 = new double[][]{{-0.0015702102444D, 111320.7020616939D, 1.704480524535203E15D, -1.033898737604234E16D, 2.611266785660388E16D, -3.51496691766537E16D, 2.659570071840392E16D, -1.072501245418824E16D, 1.800819912950474E15D, 82.5D}, {8.277824516172526E-4D, 111320.7020463578D, 6.477955746671607E8D, -4.082003173641316E9D, 1.077490566351142E10D, -1.517187553151559E10D, 1.205306533862167E10D, -5.124939663577472E9D, 9.133119359512032E8D, 67.5D}, {0.00337398766765D, 111320.7020202162D, 4481351.045890365D, -2.339375119931662E7D, 7.968221547186455E7D, -1.159649932797253E8D, 9.723671115602145E7D, -4.366194633752821E7D, 8477230.501135234D, 52.5D}, {0.00220636496208D, 111320.7020209128D, 51751.86112841131D, 3796837.749470245D, 992013.7397791013D, -1221952.21711287D, 1340652.697009075D, -620943.6990984312D, 144416.9293806241D, 37.5D}, {-3.441963504368392E-4D, 111320.7020576856D, 278.2353980772752D, 2485758.690035394D, 6070.750963243378D, 54821.18345352118D, 9540.606633304236D, -2710.55326746645D, 1405.483844121726D, 22.5D}, {-3.218135878613132E-4D, 111320.7020701615D, 0.00369383431289D, 823725.6402795718D, 0.46104986909093D, 2351.343141331292D, 1.58060784298199D, 8.77738589078284D, 0.37238884252424D, 7.45D}};
    private static final double[][] ARRAY4 = new double[][]{{1.410526172116255E-8D, 8.98305509648872E-6D, -1.9939833816331D, 200.9824383106796D, -187.2403703815547D, 91.6087516669843D, -23.38765649603339D, 2.57121317296198D, -0.03801003308653D, 1.73379812E7D}, {-7.435856389565537E-9D, 8.983055097726239E-6D, -0.78625201886289D, 96.32687599759846D, -1.85204757529826D, -59.36935905485877D, 47.40033549296737D, -16.50741931063887D, 2.28786674699375D, 1.026014486E7D}, {-3.030883460898826E-8D, 8.98305509983578E-6D, 0.30071316287616D, 59.74293618442277D, 7.357984074871D, -25.38371002664745D, 13.45380521110908D, -3.29883767235584D, 0.32710905363475D, 6856817.37D}, {-1.981981304930552E-8D, 8.983055099779535E-6D, 0.03278182852591D, 40.31678527705744D, 0.65659298677277D, -4.44255534477492D, 0.85341911805263D, 0.12923347998204D, -0.04625736007561D, 4482777.06D}, {3.09191371068437E-9D, 8.983055096812155E-6D, 6.995724062E-5D, 23.10934304144901D, -2.3663490511E-4D, -0.6321817810242D, -0.00663494467273D, 0.03430082397953D, -0.00466043876332D, 2555164.4D}, {2.890871144776878E-9D, 8.983055095805407E-6D, -3.068298E-8D, 7.47137025468032D, -3.53937994E-6D, -0.02145144861037D, -1.234426596E-5D, 1.0322952773E-4D, -3.23890364E-6D, 826088.5D}};

    /**
     * 圆周率 π
     */
    private static final double PI = 3.1415926535897932384626;

    /**
     * 长半轴 a
     */
    private static final double A = 6378245.0;

    /**
     * 离心率 ee
     */
    private static final double EE = 0.00669342162296594323;


    public static Coordinate wgs84toMercator(Coordinate wgs) {
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.wgs84toMercator(wgs);
    }

    public static Coordinate mercatorToWgs84(Coordinate mercator) {
        CrsTransformImpl ct = new CrsTransformImpl();
        Coordinate gcj = ct.mercatortoGcj02(mercator);
        return gcj02ToWgs84(gcj);
    }

    public static Coordinate mercatorToGcj02(Coordinate mercator) {
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.mercatortoGcj02(mercator);
    }

    public static Coordinate gcj02ToMercator(Coordinate gcj) {
        Coordinate wgs = gcj02ToWgs84(gcj);
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.wgs84toMercator(wgs);
    }

    public static Coordinate gcj02ToBd09(Coordinate gcj) {
        Coordinate mercator = gcj02ToMercator(gcj);
        double[] m = new double[]{mercator.x, mercator.y};
        double[] b = mercator2bd09(m);
        return new Coordinate(b[0], b[1]);
    }

    public static Coordinate bd09ToGcj02(Coordinate bd) {
        double[] b = new double[]{bd.x, bd.y};
        double[] m = bd09toMercator(b);
        Coordinate mercator = new Coordinate(m[0], m[1]);
        return mercatorToGcj02(mercator);
    }

    public static Coordinate bd09ToWgs84(Coordinate bd) {
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.decry2Wgs84fromBD09(bd);
    }

    public static Coordinate wgs84ToBd09(Coordinate wgs) {
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.encryWgs84toBD09(wgs);
    }

    public static Coordinate wgs84toGcj02(Coordinate wgs) {
        CrsTransformImpl ct = new CrsTransformImpl();
        return ct.encryWgs84toGcj02(wgs);
    }

    public static Coordinate gcj02ToWgs84(Coordinate gcj) {
        double lng = gcj.x;
        double lat = gcj.y;
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dlng = (dlng * 180.0) / (A / sqrtMagic * Math.cos(radlat) * PI);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        double wlng = lng + dlng;
        double wlat = lat + dlat;
        return new Coordinate(wlng, wlat);
    }

    private static double transformLat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    private static double[] mercator2bd09(double[] p) {
        double[] arr = null;
        double[] np = new double[]{Math.abs(p[0]), Math.abs(p[1])};

        for (int i = 0; i < ARRAY3.length; ++i) {
            if (np[1] >= ARRAY3[i]) {
                arr = ARRAY4[i];
                break;
            }
        }
        if (arr != null) {
            return convertor(np[0], np[1], arr);
        } else {
            return new double[0];
        }
    }

    private static double[] bd09toMercator(double[] p) {
        double[] arr = null;
        double lat = Math.min(p[1], 74.0D);
        lat = Math.max(lat, -74.0D);

        int i;
        for (i = 0; i < ARRAY1.length; ++i) {
            if (p[1] >= ARRAY1[i]) {
                arr = ARRAY2[i];
                break;
            }
        }
        if (arr == null) {
            for (i = ARRAY1.length - 1; i >= 0; --i) {
                if (p[1] <= -ARRAY1[i]) {
                    arr = ARRAY2[i];
                    break;
                }
            }
        }
        if (arr != null) {
            return convertor(p[0], p[1], arr);
        } else {
            return new double[0];
        }
    }

    private static double[] convertor(double x, double y, double[] param) {
        double t = param[0] + param[1] * Math.abs(x);
        double cC = Math.abs(y) / param[9];
        double cF = param[2] + param[3] * cC + param[4] * cC * cC + param[5] * cC * cC * cC + param[6] * cC * cC * cC * cC + param[7] * cC * cC * cC * cC * cC + param[8] * cC * cC * cC * cC * cC * cC;
        t *= x < 0.0D ? -1 : 1;
        cF *= y < 0.0D ? -1 : 1;
        return new double[]{t, cF};
    }
}
