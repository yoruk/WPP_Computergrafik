varying vec2 texture_coordinate;
uniform sampler2D my_color_texture;


// These vectors need to be provided by the vertex shader
// Normal direction
varying vec3 N;
// Viewer direction
varying vec3 v;
varying vec3 lightPosition;
varying vec4 color;
varying vec4 temp;

/**
 * Fragment shader used for texture mapping.
 */
void main()
{
	// Helping vectors
    vec3 L = normalize(lightPosition - v);
    vec3 E = normalize(-v);
    vec3 R = normalize(reflect(-L,N));
    
    // Phong lighting model
    vec4 ambient = gl_FrontMaterial.ambient;
    vec4 diffuse = clamp( color * abs(dot(N,L))  , 0.0, 1.0 ) ;
    vec4 spec = clamp ( gl_FrontMaterial.specular * pow(abs(dot(R,E)), gl_FrontMaterial.shininess) , 0.0, 1.0 );



	vec4 temp = texture2D(my_color_texture, texture_coordinate);

    // Sampling the texture and passing it to the frame buffer
    
    if(temp[0]<0.9 && temp[1]<0.9)
    	gl_FragColor = temp + ambient + diffuse + spec;
	else
		gl_FragColor = ambient + diffuse + spec;

    	
}