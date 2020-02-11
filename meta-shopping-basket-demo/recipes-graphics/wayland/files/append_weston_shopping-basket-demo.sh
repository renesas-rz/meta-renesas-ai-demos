#!/bin/sh

WESTON_FILE=${1}
SHOPPING_BASKET_INSTALL_DIR=${2}

cat >> "${WESTON_FILE}" <<-EOF

[launcher]
icon=${SHOPPING_BASKET_INSTALL_DIR}/icons/shopping-basket-demo.png
path=${SHOPPING_BASKET_INSTALL_DIR}/scripts/shopping-basket-demo.sh
EOF
