import os

import argostranslate.package

current_dir = os.path.dirname(os.path.abspath(__file__))

en_vi_model_path = os.path.join(current_dir, "models", "translate-en_vi-1_9.argosmodel")
vi_en_model_path = os.path.join(current_dir, "models", "translate-vi_en-1_9.argosmodel")

argostranslate.package.install_from_path(en_vi_model_path)
argostranslate.package.install_from_path(vi_en_model_path)
