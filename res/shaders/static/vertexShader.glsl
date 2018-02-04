#version 400 core

in vec3 position;
in vec4 color;

out vec4 passedColor;

void main(void) {

    gl_Position = vec4(position,1.0);
    passedColor = color;

}