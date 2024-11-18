/*using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace Lab4_2
{
    internal class Class3
    {

        public static async Task Main()
        {
            var urls = new string[]
            {
                "/documente-utile/",
                "/documente-de-infiintare/",
                "/membri-consiliului-general-cnatdcu/"
            };

            var tasks = new Task[urls.Length];

            for (int i = 0; i < urls.Length; i++)
            {
                tasks[i] = DownloadFileAsync(urls[i]);
            }

            await Task.WhenAll(tasks);

            Console.WriteLine("All downloads complete.");
        }

        private static async Task DownloadFileAsync(string path)
        {
            var entry = await Dns.GetHostEntryAsync(State.Host);
            var socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            var endpoint = new IPEndPoint(entry.AddressList[0], State.Port);
            var state = new State(socket, path);

            try
            {
                await ConnectAsync(socket, endpoint);

                await SendRequestAsync(socket, state);

                await ReceiveHeadersAsync(socket, state);

                SaveHeadersToFile(state);

            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error downloading {path}: {ex.Message}");
            }
            finally
            {
                socket.Close();
            }
        }

        // wrap BeginConnect into Task 
        private static Task ConnectAsync(Socket socket, IPEndPoint endpoint)
        {
            var tcs = new TaskCompletionSource<bool>();

            socket.BeginConnect(endpoint, ar =>
            {
                try
                {
                    socket.EndConnect(ar);
                    tcs.SetResult(true);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        // wrap BeginSend into Task 
        private static Task SendRequestAsync(Socket socket, State state)
        {
            var tcs = new TaskCompletionSource<bool>();
            var requestText = $"GET {state.Path} HTTP/1.1\r\nHost: {State.Host}\r\nConnection: close\r\n\r\n";
            var requestBytes = Encoding.UTF8.GetBytes(requestText);

            socket.BeginSend(requestBytes, 0, requestBytes.Length, SocketFlags.None, ar =>
            {
                try
                {
                    socket.EndSend(ar);
                    tcs.SetResult(true);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private static async Task ReceiveHeadersAsync(Socket socket, State state)
        {
            int bytesReceived;
            bool headersComplete = false;

            while (!headersComplete)
            {
                bytesReceived = await ReceiveChunkAsync(socket, state.Buffer);
                if (bytesReceived == 0) break; // If no bytes received, response is complete

                var responseText = Encoding.UTF8.GetString(state.Buffer, 0, bytesReceived);
                state.Content.Append(responseText);

                if (!state.HeadersParsed)
                {
                    headersComplete = ParseHeaders(state);
                }
            }
        }

        private static Task<int> ReceiveChunkAsync(Socket socket, byte[] buffer)
        {
            var tcs = new TaskCompletionSource<int>();

            socket.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, ar =>
            {
                try
                {
                    int bytesReceived = socket.EndReceive(ar);
                    tcs.SetResult(bytesReceived);
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private static bool ParseHeaders(State state)
        {
            var headersEnd = state.Content.ToString().IndexOf("\r\n\r\n", StringComparison.Ordinal);
            if (headersEnd > 0)
            {
                state.HeaderEndIndex = headersEnd + 4;

                string[] headers = state.Content.ToString().Substring(0, headersEnd).Split("\r\n");

                foreach (var header in headers)
                {
                    if (header.StartsWith("Content-Length:", StringComparison.OrdinalIgnoreCase))
                    {
                        if (int.TryParse(header.Split(':')[1].Trim(), out int contentLength))
                        {
                            state.ContentLength = contentLength;
                        }
                    }
                }

                state.HeadersParsed = true;
                return true;
            }

            return false;
        }

        private static void SaveHeadersToFile(State state)
        {
            var fileName = state.Path.Trim('/').Replace('/', '_') + "_headers.txt";
            if (string.IsNullOrWhiteSpace(fileName))
            {
                fileName = "downloaded-headers.txt";
            }

            var headers = state.Content.ToString().Substring(0, state.HeaderEndIndex);
            System.IO.File.WriteAllText(fileName, headers);

            Console.WriteLine($"Headers saved as {fileName}");
        }

        public sealed class State
        {
            public const string Host = "www.cnatdcu.ro";
            public const int Port = 80;
            public const int BufferLength = 1024;
            public readonly byte[] Buffer = new byte[BufferLength];
            public readonly StringBuilder Content = new StringBuilder();
            public readonly Socket Socket;
            public readonly string Path;

            public bool HeadersParsed = false;
            public int ContentLength = 0;
            public int HeaderEndIndex = 0;

            public State(Socket socket, string path)
            {
                Socket = socket;
                Path = path;
            }
        }
    }
}
*/