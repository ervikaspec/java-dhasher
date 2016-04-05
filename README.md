# java-dhasher

D-Hash generator for images

It generates a unique integer called D-Hash based on an algorithm for D-Hashing for images.
It is useful in checking copies of images. Two images with same D-Hash means they are visually same images.


Usage:

1. Use DHasher.hashFromBitmap(Bitmap) to get DHash value from Bitmap blob (Android Bitmaps used here)

2. Use DHasher.hashFromPath(String) to get DHash value from an image file path. It will load image in memory as a Bitmap blob (Android Bitmaps used here)

Returns:

A String representing DHash integer value.
