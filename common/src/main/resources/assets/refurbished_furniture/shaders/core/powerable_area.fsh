#version 330

#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec2 textureUV;
in vec3 vertexPos;

out vec4 fragColor;

const float SMALL_AREA_FAR = 20.0;
const float SMALL_AREA_INTENSITY = 5.0;
const float LARGE_AREA_FAR = 100.0;
const float LARGE_AREA_INTENSITY = 0.5;
const vec3 WHITE = vec3(1.0);

void main() {
    vec4 color = texture(Sampler0, textureUV);
    if (color.a == 0.0) {
        discard;
    }
    vec4 areaColor = color;
    float largeAreaAlpha = 1.0 - smoothstep(0.0, LARGE_AREA_FAR, length(vertexPos));
    largeAreaAlpha *= LARGE_AREA_INTENSITY;
    float smallAreaAlpha = 1.0 - smoothstep(0.0, SMALL_AREA_FAR, length(vertexPos));
    smallAreaAlpha *= SMALL_AREA_INTENSITY;
    float areaAlpha = max(smallAreaAlpha, largeAreaAlpha);
    areaAlpha = clamp(areaAlpha, 0, 1);
    vec3 areaColorRgb = mix(WHITE, areaColor.rgb, areaAlpha);
    fragColor = vec4(areaColorRgb, areaColor.a * areaAlpha);
}
