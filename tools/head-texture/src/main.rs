use base64::{encode, decode, DecodeError};
use serde::{Deserialize, Serialize};
use serde_json::Error as JsonError;

fn main() {
    let (args, _) = rustop::opts! {
        synopsis "Strips extra data from base64 texture to make it perfectly compatible";
        opt base64:String, desc:"A base64 encoded player head texture";
    }.parse_or_exit();

    match strip(&args.base64) {
        Ok((json_out, base64_out)) => println!("{json_out}\n{base64_out}"),
        Err(error) => println!("{:#?}", error),
    }
}

fn strip(base64_in: &str) -> Result<(String, String), StripError> {
    let decoded = decode(&base64_in).map_err(|e| StripError::DecodeBase64(e))?;
    let json_in: Base64Texture = serde_json::from_slice(&decoded).map_err(|e| StripError::DeserializeJson(e))?;
    let json_out = serde_json::to_string(&json_in).map_err(|e| StripError::SerializeJson(e))?;
    let encoded = encode(&json_out);

    Ok((json_out, encoded))
}

#[derive(Debug)]
enum StripError {
    DecodeBase64(DecodeError),
    DeserializeJson(JsonError),
    SerializeJson(JsonError),
}

#[derive(Debug, Deserialize, Serialize)]
struct Base64Texture {
    #[serde(rename = "timestamp", skip)]
    _timestamp: u64,
    #[serde(rename = "profileId", skip)]
    _profile_id: String,
    #[serde(rename = "profileName", skip)]
    _profile_name: String,
    #[serde(rename = "signatureRequired", skip)]
    _signature_required: bool,
    textures: Textures,
}

#[derive(Debug, Deserialize, Serialize)]
struct Textures {
    #[serde(rename = "SKIN")]
    skin: Skin,
}

#[derive(Debug, Deserialize, Serialize)]
struct Skin {
    url: String,
    #[serde(rename = "metadata", skip)]
    _metadata: Metadata,
}

#[derive(Debug, Default, Deserialize, Serialize)]
struct Metadata {
    model: String,
}
