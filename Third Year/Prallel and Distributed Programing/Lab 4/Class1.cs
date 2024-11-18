using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace Lab4_2
{
    internal class Class1
    {
        private static readonly List<string> urls = new List<string>
        {
            "/documente-utile/",
            "/documente-de-infiintare/",
            "/membri-consiliului-general-cnatdcu/"

        };

        public static void Main()
        {
            var downloadTasks = new List<ManualResetEvent>();

            foreach (var url in urls)
            {
                var downloadComplete = new ManualResetEvent(false);  //unsignaled in the beginning meaning the task is not done yet
                downloadTasks.Add(downloadComplete);
                DownloadFile(url, downloadComplete);
            }

            WaitHandle.WaitAll(downloadTasks.ToArray());
            Console.WriteLine("All downloads complete.");
        }

        private static void DownloadFile(string path, ManualResetEvent downloadComplete)
        {
            var entry = Dns.GetHostEntry(State.Host);
            var socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            var endpoint = new IPEndPoint(entry.AddressList[0], State.Port);
            var state = new State(socket, path, downloadComplete);

            state.Socket.BeginConnect(endpoint, ConnectCallback, state);
        }

        private static void ConnectCallback(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            state.Socket.EndConnect(ar);

            var requestText = $"GET {state.Path} HTTP/1.1\r\nHost: {State.Host}\r\nConnection: close\r\n\r\n";
            var requestBytes = Encoding.UTF8.GetBytes(requestText);

            state.Socket.BeginSend(requestBytes, 0, requestBytes.Length, SocketFlags.None, SendCallback, state);
        }

        private static void SendCallback(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            state.Socket.EndSend(ar);

            state.Socket.BeginReceive(state.Buffer, 0, State.BufferLength, SocketFlags.None, ReceiveCallback, state);
        }

        private static void ReceiveCallback(IAsyncResult ar)
        {
            var state = (State)ar.AsyncState;
            var bytesReceived = state.Socket.EndReceive(ar);

            if (bytesReceived > 0)
            {
                var responseText = Encoding.UTF8.GetString(state.Buffer, 0, bytesReceived);
                state.Content.Append(responseText);

                // if headers not parsed
                if (!state.HeadersParsed)
                {
                    ParseHeaders(state);
                }

                //  all content has been received
                if (state.HeadersParsed && state.Content.Length >= state.ContentLength + state.HeaderEndIndex)
                {
                    Console.WriteLine($"Download complete for {state.Path}."); a

                    SaveContentToFile(state);

                    state.ReceiveDone.Set();
                    state.DownloadComplete.Set(); // Signal the download completion
                    state.Socket.Close();
                }
                else
                {
                    // Continue receiving if more data is expected
                    state.Socket.BeginReceive(state.Buffer, 0, State.BufferLength, SocketFlags.None, ReceiveCallback, state);
                }
            }
            else
            {
                state.ReceiveDone.Set();
                state.DownloadComplete.Set();
                state.Socket.Close();
            }
        }

        // Method to save downloaded content to a file
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





        private static void ParseHeaders(State state)
        {
            var headersEnd = state.Content.ToString().IndexOf("\r\n\r\n", StringComparison.Ordinal);
            if (headersEnd > 0)
            {
                state.HeaderEndIndex = headersEnd + 4; // Index after headers
                var headers = state.Content.ToString().Substring(0, headersEnd).Split("\r\n");

                Console.WriteLine("Headers received:");
                foreach (var header in headers)
                {
                    Console.WriteLine(header);
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




        public sealed class State
        {
            public const string Host = "www.cnatdcu.ro";
            public const int Port = 80;
            public const int BufferLength = 1024;

            public readonly byte[] Buffer = new byte[BufferLength];
            public readonly ManualResetEvent ConnectDone = new ManualResetEvent(false);
            public readonly ManualResetEvent ReceiveDone = new ManualResetEvent(false);
            public readonly ManualResetEvent SendDone = new ManualResetEvent(false);
            public readonly ManualResetEvent DownloadComplete;
            public readonly StringBuilder Content = new StringBuilder();
            public readonly Socket Socket;
            public readonly string Path;
            public bool HeadersParsed = false;
            public int ContentLength = 0;
            public int HeaderEndIndex = 0;

            public State(Socket socket, string path, ManualResetEvent downloadComplete)
            {
                Socket = socket;
                Path = path;
                DownloadComplete = downloadComplete;
            }
        }
    }
}
