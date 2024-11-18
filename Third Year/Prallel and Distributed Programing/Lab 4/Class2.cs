using System.Net.Sockets;
using System.Net;
using System.Text;

namespace Lab4_2
{
    internal class Class2
    {
        private static readonly List<string> urls = new List<string>
        {
            "/documente-utile/",
            "/membri-consiliului-general-cnatdcu/",
            "/documente-de-infiintare/"

        };

        public static async Task Main()
        {
            List<Task> downloadTasks = new List<Task>();

            foreach (var url in urls)
            {
                downloadTasks.Add(DownloadFileAsync(url));
            }

            await Task.WhenAll(downloadTasks);

            Console.WriteLine("All downloads complete.");
        }

        private static async Task DownloadFileAsync(string path)
        {
            var entry = await Dns.GetHostEntryAsync(State.Host);
            var socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            var endpoint = new IPEndPoint(entry.AddressList[0], State.Port);

            var state = new State(socket, path);

            // Connect to server
            await ConnectAsync(socket, endpoint);

            string requestText = $"GET {path} HTTP/1.1\r\nHost: {State.Host}\r\nConnection: close\r\n\r\n";
            byte[] requestBytes = Encoding.UTF8.GetBytes(requestText);
            await SendAsync(socket, requestBytes);

            await ReceiveAsync(state);

            SaveContentToFile(state);
        }

        private static Task ConnectAsync(Socket socket, EndPoint endpoint)
        {
            var tcs = new TaskCompletionSource();
            socket.BeginConnect(endpoint, ar =>
            {
                try
                {
                    socket.EndConnect(ar);
                    tcs.SetResult();
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private static Task SendAsync(Socket socket, byte[] data)
        {
            var tcs = new TaskCompletionSource();
            socket.BeginSend(data, 0, data.Length, SocketFlags.None, ar =>
            {
                try
                {
                    socket.EndSend(ar);
                    tcs.SetResult();
                }
                catch (Exception ex)
                {
                    tcs.SetException(ex);
                }
            }, null);

            return tcs.Task;
        }

        private static async Task ReceiveAsync(State state)
        {
            while (true)
            {
                var bytesReceived = await ReceiveChunkAsync(state.Socket, state.Buffer);

                if (bytesReceived == 0)
                    break;

                string responseText = Encoding.UTF8.GetString(state.Buffer, 0, bytesReceived);
                state.Content.Append(responseText);

                if (!state.HeadersParsed)
                {
                    ParseHeaders(state);
                }

                if (state.HeadersParsed && state.Content.Length >= state.ContentLength + state.HeaderEndIndex)
                {
                    break;
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

        private static void ParseHeaders(State state)
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
            }
        }

        private static void SaveContentToFile(State state)
        {
            var fileName = state.Path.Trim('/').Replace('/', '_');
            if (string.IsNullOrWhiteSpace(fileName))
            {
                fileName = "downloaded-file.txt";
            }

            var bodyContent = state.Content.ToString().Substring(state.HeaderEndIndex);
            System.IO.File.WriteAllText(fileName, bodyContent);

            Console.WriteLine($"File saved as {fileName}");
        }

        public sealed class State
        {
            public const string Host = "www.cnatdcu.ro";
            public const int Port = 80;
            public const int BufferLength = 1024;

            public readonly byte[] Buffer = new byte[BufferLength];
            public readonly Socket Socket;
            public readonly string Path;
            public readonly StringBuilder Content = new StringBuilder();

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
