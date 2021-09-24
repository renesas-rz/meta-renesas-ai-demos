#!/bin/sh

WESTON_FILE=${1}
SHOPPING_BASKET_INSTALL_DIR=${2}

cat >> "${WESTON_FILE}" <<-EOF

[output]
name=HDMI-A-1
mode=1280x720@60.0

[launcher]
icon=${SHOPPING_BASKET_INSTALL_DIR}/icons/shopping-basket-demo.png
path=${SHOPPING_BASKET_INSTALL_DIR}/scripts/shopping-basket-demo.sh
EOF
