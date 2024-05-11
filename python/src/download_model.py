from huggingface_hub import hf_hub_download

model_path = hf_hub_download(
    repo_id='TheBloke/openchat-3.5-0106-GGUF',
    filename='openchat-3.5-0106.Q5_K_M.gguf',
    local_dir='../../models'
)

# model_path = hf_hub_download(
    # repo_id='meta-llama/Meta-Llama-3-8B',
    # filename='Meta-Llama-3-8B.gguf',
    # local_dir='../../models'
# )


model_path
