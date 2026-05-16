/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Se encarga de encriptar información a la BD y viceversa
 * 
 * @author Andre
 */
public class Encriptador {
    private static final String ALGORITMO = "AES/GCM/NoPadding";
    private static final int LONGITUD_LLAVE = 256;
    private static final int LONGITUD_TAG = 128; 
    private static final int LONGITUD_IV = 12;
    
    private static final SecretKey LLAVE_SECRETA;

    //Bloque estático para generar la llave una sola vez al cargar la clase
    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(LONGITUD_LLAVE);
            LLAVE_SECRETA = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error inicializando la llave", e);
        }
    }
    
    /**
     * Encripta un texto plano y devuelve el resultado en Base64 seguro
     */
    public static String encriptar(String texto) {
        try {
            byte[] iv = new byte[LONGITUD_IV];
            SecureRandom.getInstanceStrong().nextBytes(iv);
            byte[] textoEncriptado = encriptar(texto, LLAVE_SECRETA, iv);
            
            byte[] ivYTextoEncriptado = ByteBuffer.allocate(iv.length + textoEncriptado.length)
                    .put(iv)
                    .put(textoEncriptado)
                    .array();
            
            return Base64.getEncoder().encodeToString(ivYTextoEncriptado);
        } catch (Exception e) {
            System.err.println("Error al encriptar: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Desencripta un texto usando el proceso inverso
     * 
     * @param textoEncriptadoBase64
     * @return 
     */
    public static String desencriptar(String textoEncriptadoBase64) {
        try {
            //Decodifica
            byte[] ivYTextoEncriptado = Base64.getDecoder().decode(textoEncriptadoBase64);
            
            //Extrae bytes
            byte[] iv = new byte[LONGITUD_IV];
            ByteBuffer buffer = ByteBuffer.wrap(ivYTextoEncriptado);
            buffer.get(iv);
            byte[] textoEncriptado = new byte[buffer.remaining()];
            buffer.get(textoEncriptado);
            
            //Desencripta
            return desencriptar(textoEncriptado, LLAVE_SECRETA, iv);
        } catch (Exception e) {
            System.err.println("Error al desencriptar: " + e.getMessage());
            return null;
        }
    }
    
    
    private static byte[] encriptar(String datos, SecretKey llave, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        GCMParameterSpec spec = new GCMParameterSpec(LONGITUD_TAG, iv);
        cipher.init(Cipher.ENCRYPT_MODE, llave, spec);
        return cipher.doFinal(datos.getBytes(StandardCharsets.UTF_8));
    }


    private static String desencriptar(byte[] datosEncriptados, SecretKey llave, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        GCMParameterSpec spec = new GCMParameterSpec(LONGITUD_TAG, iv);
        cipher.init(Cipher.DECRYPT_MODE, llave, spec);
        byte[] datosDesencriptados = cipher.doFinal(datosEncriptados);
        return new String(datosDesencriptados, StandardCharsets.UTF_8);
    }
}