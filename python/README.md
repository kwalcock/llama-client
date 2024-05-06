# Python

This project requires at least [Python 3.10](https://www.python.org/downloads/release/python-31011/), which you may have to [build](https://pages.github.nceas.ucsb.edu/NCEAS/Computing/local_install_python_on_a_server.html).

The directory structure of the overall project is intended to look like this:

* llama-client-project
  * corpora
  * llama-client
    * python
      * pyproject.toml
    * scala
  * models
    * openchat-3.5-0106.Q5_K_M.gguf
  * venv
    * bin
      * activate

Here are the steps that can create such a structure:

* Create the project directory
  * mkdir llama-client-project
  * cd llama-client-project
  * mkdir corpora
  * mkdir models
* Clone the repo
  * git clone https://github.com/kwalcock/llama-client
* Create a virtual environment
  * python3 -m venv venv
  * source venv/bin/activate (for Lin/Mac)
  * ./venv/Scripts/activate (for Win)
  * cd llama-client/python
  * pip3 install .
* [Install llama_cpp_python](https://llama-cpp-python.readthedocs.io/en/latest/install)
  * pip3 install llama-cpp-python
* Download the model
  * python3 src/download_model.py
* Run other programs
  * python3 src/crops.py
