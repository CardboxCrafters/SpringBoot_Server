package com.mycompany.myapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageUtil {
    /*
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static byte[] cropImage(MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();

        Mat src = Imgcodecs.imdecode(new MatOfByte(imageData), Imgcodecs.IMREAD_UNCHANGED);

        // 이미지 전처리
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);
        Mat edged = new Mat();
        Imgproc.Canny(blurred, edged, 50, 200);

        // 윤곽선 찾기
        MatOfPoint2f cardContour = findCardContour(edged);

        if (cardContour != null) {
            // 카드 영역 자르기
            Mat croppedImage = cropCard(src, cardContour);

            // 이미지를 byte 배열로 변환하여 반환
            return matToByteArray(croppedImage);
        } else {
            System.out.println("No business card detected.");
            return null;
        }
    }

    private static MatOfPoint2f findCardContour(Mat edged) {
        MatOfPoint2f cardContour = new MatOfPoint2f();
        Mat hierarchy = new Mat(); // 계층 구조를 저장할 필요 없음
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edged, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            double peri = Imgproc.arcLength(contour2f, true);
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(contour2f, approx, 0.02 * peri, true);
            if (approx.total() == 4) { // 근사 다각형이 4개의 꼭지점을 갖는 경우
                cardContour = approx;
                break;
            }
        }
        return cardContour;
    }

    private static Mat cropCard(Mat src, MatOfPoint2f cardContour) {
        Core.flip(src, src, 1);

        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Imgproc.approxPolyDP(cardContour, approxCurve, Imgproc.arcLength(cardContour, true) * 0.02, true);
        Point[] points = approxCurve.toArray();
        Point topLeft = points[0];
        Point topRight = points[1];
        Point bottomRight = points[2];
        Point bottomLeft = points[3];

        // 최대 너비와 높이 계산
        double widthA = Math.sqrt(Math.pow(bottomRight.x - bottomLeft.x, 2) + Math.pow(bottomRight.y - bottomLeft.y, 2));
        double widthB = Math.sqrt(Math.pow(topRight.x - topLeft.x, 2) + Math.pow(topRight.y - topLeft.y, 2));
        int maxWidth = (int) Math.max(widthA, widthB);

        double heightA = Math.sqrt(Math.pow(topRight.x - bottomRight.x, 2) + Math.pow(topRight.y - bottomRight.y, 2));
        double heightB = Math.sqrt(Math.pow(topLeft.x - bottomLeft.x, 2) + Math.pow(topLeft.y - bottomLeft.y, 2));
        int maxHeight = (int) Math.max(heightA, heightB);

        MatOfPoint2f destPoints = new MatOfPoint2f(new Point(0, 0), new Point(maxWidth - 1, 0),
                new Point(maxWidth - 1, maxHeight - 1), new Point(0, maxHeight - 1));
        Mat perspectiveTransform = Imgproc.getPerspectiveTransform(cardContour, destPoints);
        Mat croppedImage = new Mat(maxHeight, maxWidth, CvType.CV_8UC3);
        Imgproc.warpPerspective(src, croppedImage, perspectiveTransform, new Size(maxWidth, maxHeight));

        Core.rotate(croppedImage, croppedImage, Core.ROTATE_90_CLOCKWISE);

        return croppedImage;
    }

    private static byte[] matToByteArray(Mat croppedImage) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", croppedImage, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        matOfByte.release();
        return byteArray;
    }
     */
}