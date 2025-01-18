package org.jesperancinha.smtd
import java.io.File
import java.io.IOException

fun applyWarpTransition(
    inputVideo: String,
    outputVideo: String,
    transitionTime: Int
) {
    // Ensure FFmpeg is installed and available in the system PATH
    val ffmpegCommand = listOf(
        "ffmpeg",
        "-i", inputVideo,                // Input video
        "-filter_complex",
        "[0:v]split[v1][v2];" +
                "[v1]setpts=PTS-STARTPTS[v1a];" +
                "[v2]geq='lum(X,Y)':cb(X,Y):cr(X,Y)[v2a];" +
                "[v1a][v2a]blend=all_expr='if(lte(T,$transitionTime),A,B)'[outv]",
        "-map", "[outv]",
        "-map", "0:a?",
        "-y", outputVideo                // Output video
    )

    try {
        val process = ProcessBuilder(ffmpegCommand)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()

        val exitCode = process.waitFor()
        if (exitCode == 0) {
            println("Warp transition applied successfully! Output saved to $outputVideo")
        } else {
            println("Error occurred while applying warp transition.")
        }
    } catch (e: IOException) {
        println("Error: FFmpeg not found or failed to execute.")
        e.printStackTrace()
    } catch (e: InterruptedException) {
        println("Error: The process was interrupted.")
        e.printStackTrace()
    }
}

fun main() {
    // Input parameters
    val inputVideo = "input.mp4" // Replace with your input video file
    val outputVideo = "output.mp4" // Replace with your desired output file name
    val transitionTime = 2 // Warp transition time in seconds

    // Ensure input video file exists
    if (!File(inputVideo).exists()) {
        println("Error: Input video file '$inputVideo' does not exist.")
        return
    }

    // Apply the warp transition
    applyWarpTransition(inputVideo, outputVideo, transitionTime)
}
