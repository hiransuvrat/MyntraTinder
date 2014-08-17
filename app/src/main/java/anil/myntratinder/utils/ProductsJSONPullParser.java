package anil.myntratinder.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import anil.myntratinder.models.Product;

/**
 * Created by Anil on 7/18/2014.
 * http json response structure
 * {
 *     response1: {
 *         totalProductsCount: 3012,
 *         filters: {
 *
 *         },
 *         products: [
 *         {
 *             sizes: String,
 *             stylename: String,
 *             search_image: String,
 *             discounted_price: Int?,
 *             discount: Int,
 *             id: Int,
 *             product: String,
 *             imageEntry_default: String,
 *             price: Int,
 *             styleid: Int,
 *             etc..
 *         },
 *         {}
 *         ]
 *     }
 * }
 */
public class ProductsJSONPullParser {

    public static List<Product> getProductsFromFile(Context context, String filename){
        /* todo: check if the we are parsing the json properly. extract extra info for each property if needed
         * http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
         */
        List<Product> products = new ArrayList<Product>();
        String json = null;
        Product product = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            json = new String(buffer, "UTF-8");
            if (json != null){
                JSONObject jsonObject = new JSONObject(json);
                JSONObject response1 = jsonObject.getJSONObject("response1");
                JSONArray productObjects = response1.getJSONArray("products");
                for (int i = 0; i < productObjects.length(); i++) {
                    JSONObject p = productObjects.getJSONObject(i);
                    product = new Product(i);
                    product.setDiscount(p.getString("discount"));
                    product.setPrice(p.getString("price"));
                    product.setStyleId(p.getString("styleid"));
                    product.setDreLandingPageUrl(p.getString("dre_landing_page_url"));
                    product.setImageUrl(p.getString("search_image"));
                    product.setDiscountedPrice(p.getString("discounted_price"));
                    product.setStyleName(p.getString("stylename"));
                    products.add(product);
                    /*
                     * todo: get image url from the json object imageEntry_default, rather than search_image
                     * todo: add more getter and setters to the Product class to extract even more data from json object
                     */
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Product> getProductsFromFileAndInsertGroupLabel(Context context, String filename, String groupLabel){
        /* todo: check if the we are parsing the json properly. extract extra info for each property if needed
         * http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
         */
        List<Product> products = new ArrayList<Product>();
        String json = null;
        Product product = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            json = new String(buffer, "UTF-8");
            if (json != null){
                JSONObject jsonObject = new JSONObject(json);
                JSONObject response1 = jsonObject.getJSONObject("response1");
                JSONArray productObjects = response1.getJSONArray("products");
                for (int i = 0; i < productObjects.length(); i++) {
                    JSONObject p = productObjects.getJSONObject(i);
                    product = new Product(groupLabel);
                    product.setDiscount(p.getString("discount"));
                    product.setPrice(p.getString("price"));
                    product.setStyleId(p.getString("styleid"));
                    product.setDreLandingPageUrl(p.getString("dre_landing_page_url"));
                    product.setImageUrl(p.getString("search_image"));
                    product.setDiscountedPrice(p.getString("discounted_price"));
                    product.setStyleName(p.getString("stylename"));
                    products.add(product);
                    /*
                     * todo: get image url from the json object imageEntry_default, rather than search_image
                     * todo: add more getter and setters to the Product class to extract even more data from json object
                     */
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }


    // todo: get image url from imageEntry_default, you can control the image quality
    // if you parse imageEntry_default you can get image details like relativePath, supportedResolutions, domain, path, resolutionFormula, securedDomain etc
    // fixme: instead of returning String return the JSONObject json here,
    // add additional properties to Product model like relativePath, domain etc for image for now just use search_image
    // product.setImageUrl(getImageUrlFromJson(p.getString("imageEntry_default")));
    public static String getImageUrlFromJson(String imageEntry_default){
        String imageUrl = "";
        try {
            if (imageEntry_default != null){
                JSONObject json = new JSONObject(imageEntry_default);
                imageUrl = json.getString("path");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }


}
