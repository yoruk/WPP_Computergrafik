varying vec2 texture_coordinate;

varying vec3 N;
varying vec3 v;
varying vec3 lightPosition;
varying vec4 color;

/**
 * Vertex shader used for texturing. No color computations
 * are required here, the information is passed to the
 * corresponding fragment shader.
 */
void main()
{
    // Eye position
    v = vec3(gl_ModelViewMatrix * gl_Vertex);
    // Surface normal
    N = normalize(gl_NormalMatrix * gl_Normal);
    // Light position
    lightPosition = gl_NormalMatrix * gl_LightSource[0].position.xyz;




    // Position in 3-space
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    
    // Just pass the texture coordinate at the vertex to the fragment
    // shader.
    
    

    	texture_coordinate = vec2(gl_MultiTexCoord0);

    	color = gl_Color;
    
    




    
}