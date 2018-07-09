#version 400 core

in vec3 position;
in vec4 color;
in vec2 uvCoordinates;

out vec4 passedColor;
out vec2 passedUvCoordinates;

uniform mat4 transformationMatrix;

void main(void) {

    gl_Position = vec4(position,1.0) * transformationMatrix;
    passedColor = color;
    passedUvCoordinates = uvCoordinates;

}