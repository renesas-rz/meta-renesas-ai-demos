FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
	file://0001-download-dependencies-Update-EIGEN_URL.patch \
"

# TensorFlow Lite Makefile based build system does not generate a .so file,
# this statement makes sure package -staticdev goes where package -dev goes,
# as package -staticdev contains the .a file.
RDEPENDS_${PN}-dev += "${PN}-staticdev"
