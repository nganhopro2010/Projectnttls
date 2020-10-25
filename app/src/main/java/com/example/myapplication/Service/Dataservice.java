package com.example.myapplication.Service;

import com.example.myapplication.Model.BannerDanhMuc;
import com.example.myapplication.Model.ChiTietSanPham;
import com.example.myapplication.Model.Customer;
import com.example.myapplication.Model.DanhMuc;
import com.example.myapplication.Model.QuangCao;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.SliderSanPham;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.TrangThai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dataservice {
    @GET("register.php")
    Call<Customer>performRegistration(@Query("cusName") String cusName, @Query("cusEmail")
            String cusEmail,@Query("cusTel") String cusTel, @Query("cusPass")
            String cusPass,@Query("cusBirthday")  String cusBirthday,@Query("cusAddress")
            String cusAddress);

    @GET("login.php")
    Call<Customer>performUserLogin(@Query("cusEmail") String cusEmail,@Query("cusPass")
            String cusPass);

    @GET("quangcao.php")
    Call<List<QuangCao>>GetDataQuangCao();

    @GET("danhmuc.php")
    Call<List<DanhMuc>>GetDataDanhMuc();

    @FormUrlEncoded
    @POST("bannerDanhMuc.php")
    Call<List<BannerDanhMuc>> GetDataBannerTheoDanhMuc(@Field("catId") String catId);

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> GetSanPhamTheoDanhMuc(@Field("catId") String catId);

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> ThemLuotXem(@Field("customerId") String customerId, @Field("productId") String productId);

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> ThemLuotXemProduct(@Field("productIdLuotXem") String productId);

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> GetSanPhamMoiDanhMuc(@Field("catIdSPMoi") String catIdSPMoi);

    @FormUrlEncoded
    @POST("sliderSanPham.php")
    Call<List<SliderSanPham>> GetDataSliderSanPham(@Field("productId") String productId);

    @FormUrlEncoded
    @POST("chiTietSanPham.php")
    Call<List<ChiTietSanPham>> GetChiTietSanPham(@Field("productId") String productId);

    @FormUrlEncoded
    @POST("themVaoGio.php")
    Call<GioHang> ThemVaoGio(@Field("productId") String productId, @Field("customerId")
            String customerId,@Field("soLuong") int soLuong);

    @FormUrlEncoded
    @POST("xoaGioHang.php")
    Call<String>XoaGioHang(@Field("productId") String productId, @Field("customerId") String customerId);

    @FormUrlEncoded
    @POST("gioHang.php")
    Call<List<SanPham>>GetGioHang(@Field("customerId") String customerId);

    @GET("tongTienGioHang.php")
    Call<TrangThai>GetTongTien(@Query("customerIdPrice") String customerIdPrice);

    @GET("maGiamGia.php")
    Call<TrangThai>GetMaGiamGia(@Query("customerIdPrice") String customerIdPrice, @Query("codeSale") String codeSale);

    @GET("checkCodeSale.php")
    Call<TrangThai>CheckCodeSale(@Query("codeSale") String codeSale, @Query("productId") String productId);

    @GET("shopping.php")
    Call<TrangThai>GetShopping(@Query("customerId") String customerId, @Query("cusEmail")
            String cusEmail, @Query("cusTel") String cusTel, @Query("cusAddress") String cusAddress, @Query("tongTien") String tongTien, @Query("khuyenMai") String khuyenMai);

    @GET("shoppingNow.php")
    Call<TrangThai>GetShoppingNow(@Query("customerId") String customerId, @Query("cusEmail")
            String cusEmail, @Query("cusTel") String cusTel, @Query("cusAddress") String cusAddress, @Query("tongTien") String tongTien, @Query("khuyenMai") String khuyenMai, @Query("productId") String productId, @Query("soLuong") int soLuong);

    @FormUrlEncoded
    @POST("timKiemSanPham.php")
    Call<List<SanPham>> GetSearchSanPham(@Field("tukhoa") String tukhoa);

    @GET("sanPhamTop20.php")
    Call<List<SanPham>> GetSanPhamTop20();

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> GetSanPhamDaXem(@Field("customerIdViewers") String customerIdViewers);

    @GET("sanPhamForCurrentDay.php")
    Call<List<SanPham>>GetSanPhamForCurrentDay();

    @GET("capNhatThongTin.php")
    Call<TrangThai>CapNhatThongTin(@Query("customerId") String customerId, @Query("cusTel") String cusTel, @Query("cusPass") String cusPass, @Query("cusAddress") String cusAddress, @Query("cusBirthday") String cusBirthday);

    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> ThemYeuThich(@Field("customerIdYeuThich") String customerId, @Field("productIdYeuThich") String productId);


    @FormUrlEncoded
    @POST("sanPham.php")
    Call<List<SanPham>> GetYeuThich(@Field("customerIdDaYeu") String customerIdDaYeu);

    @FormUrlEncoded
    @POST("xoaYeuThich.php")
    Call<String> XoaYeuThich(@Field("productId") String productId, @Field("customerId") String customerId);
}
