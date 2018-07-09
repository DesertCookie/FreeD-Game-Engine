#version 400 core

in vec4 passedColor;
in vec2 passedUvCoordinates;

out vec4 outColor;

uniform sampler2D textureSampler;

void main(void) {

    outColor = passedColor * texture(textureSampler, passedUvCoordinates);

}