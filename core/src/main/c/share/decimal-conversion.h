/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class io_questdb_std_DecimalConversion */

#ifndef _Included_io_questdb_std_DecimalConversion
#define _Included_io_questdb_std_DecimalConversion
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     io_questdb_std_DecimalConversion
 * Method:    appendDouble0
 * Signature: (JD)I
 */
JNIEXPORT jint JNICALL Java_io_questdb_std_DecimalConversion_appendDouble0__JD
        (JNIEnv *, jclass, jlong, jdouble);

/*
 * Class:     io_questdb_std_DecimalConversion
 * Method:    appendDouble0
 * Signature: (JDI)I
 */
JNIEXPORT jint JNICALL Java_io_questdb_std_DecimalConversion_appendDouble0__JDI
        (JNIEnv *, jclass, jlong, jdouble, jint);

/*
 * Class:     io_questdb_std_DecimalConversion
 * Method:    appendFloat0
 * Signature: (JFI)I
 */
JNIEXPORT jint JNICALL Java_io_questdb_std_DecimalConversion_appendFloat0
        (JNIEnv *, jclass, jlong, jfloat);

/*
 * Class:     io_questdb_std_DecimalConversion
 * Method:    parseDouble
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_io_questdb_std_DecimalConversion_parseDouble
        (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     io_questdb_std_DecimalConversion
 * Method:    parseFloat
 * Signature: (JI)F
 */
JNIEXPORT jfloat JNICALL Java_io_questdb_std_DecimalConversion_parseFloat
        (JNIEnv *, jclass, jlong, jint);

#ifdef __cplusplus
}
#endif
#endif
