#!/bin/sh

INSTALL_DIRECTORY=${1}

mkdir -p scripts

cat > "scripts/cpu-object.sh" <<-EOF
#!/bin/sh

cd ${INSTALL_DIRECTORY}
./object_detection_demo
EOF

cat > "scripts/tpu-object.sh" <<-EOF
#!/bin/sh

cd ${INSTALL_DIRECTORY}
./object_detection_demo -t
EOF

cat > "scripts/cpu-face.sh" <<-EOF
#!/bin/sh

cd ${INSTALL_DIRECTORY}
./object_detection_demo -m mobilenet_ssd_v2_face_quant_postprocess.tflite \\
	-l face_label.txt
EOF

cat > "scripts/tpu-face.sh" <<-EOF
#!/bin/sh

cd ${INSTALL_DIRECTORY}
./object_detection_demo -m mobilenet_ssd_v2_face_quant_postprocess_edgetpu.tflite \\
	-l face_label.txt -t
EOF
