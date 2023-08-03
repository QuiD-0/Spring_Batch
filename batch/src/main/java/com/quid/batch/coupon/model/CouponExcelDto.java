package com.quid.batch.coupon.model;

import com.quid.batch.common.excelDownloader.FieldsHeaders;
import com.quid.batch.common.excelDownloader.Headers;
import com.quid.batch.common.excelDownloader.Internationalization;
import com.quid.batch.coupon.domain.Coupon;
import java.time.format.DateTimeFormatter;

@FieldsHeaders(i18n = {
    @Headers(code = Internationalization.KO, fields = {"사용자 ID", "사용자 이름", "할인율", "발급일", "만료일", "쿠폰타입"}),
})
public record CouponExcelDto(
    Long userId,
    String name,
    Integer discount,
    String createdAt,
    String expiredAt,
    String couponType
) {

    public static CouponExcelDto of(Coupon coupon) {
        return new CouponExcelDto(
            coupon.getUserId(),
            coupon.getName(),
            coupon.getDiscount(),
            coupon.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            coupon.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            coupon.getCouponType().toString()
        );
    }
}
